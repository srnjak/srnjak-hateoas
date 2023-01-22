package com.srnjak.hateoas.mediatype.hal.xml;

import com.srnjak.hateoas.LinkRelation;
import com.srnjak.hateoas.relation.*;
import com.srnjak.hateoas.mediatype.hal.CurieExtractor;
import com.srnjak.hateoas.mediatype.hal.HalLinkObjectEntry;
import com.srnjak.hateoas.mediatype.hal.HalObject;
import com.srnjak.hateoas.utils.GenericEntityWrapper;
import lombok.AllArgsConstructor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A xml serializer of a {@link HalObject} object.
 */
@AllArgsConstructor
public class HalObjectXml {

    public static final String MISSING_SELF_LINK = "Missing SELF link";
    /**
     * The hal representation of an entity object
     */
    private HalObject halObject;

    /**
     * Transforms into a hal representation of an entity in a XML document.
     *
     * @return The XML document
     */
    public Document toXmlDocument() {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        DocumentBuilder db;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        Document doc = db.newDocument();
        doc.setXmlStandalone(true);

        Element rootResource =
                toResourceElement(doc, IanaLinkRelation.SELF);

        // curie
        CurieExtractor extractor = new CurieExtractor(this.halObject);
        Set<CurieDefinition> curieDefinitions = extractor.extract();

        curieDefinitions.forEach(c -> {
            rootResource.setAttributeNS(
                    XMLConstants.XMLNS_ATTRIBUTE_NS_URI,
                    "xmlns:" + c.getPrefix(),
                    c.getHref());
        });

        doc.appendChild(rootResource);

        return doc;
    }

    /**
     * Transforms into a resource element.
     *
     * @param doc The document to which the new element will be pointing to.
     * @param rel The relation to the resource
     * @return The resource element
     */
    Element toResourceElement(Document doc, LinkRelation rel) {
        Element element = doc.createElement("resource");

        Optional.ofNullable(halObject.getHalLinks())
                .flatMap(links -> links.get(IanaLinkRelation.SELF))
                .filter(e -> e instanceof HalLinkObjectEntry)
                .map(e -> (HalLinkObjectEntry) e)
                .map(HalLinkObjectEntry::getHalLink)
                .ifPresentOrElse(l -> {
                    element.setAttribute("rel", rel.getValue());
                    HalLinksXml.setLinkAttributesToElement(element, l);
                },
                () -> {
                    throw new IllegalArgumentException(MISSING_SELF_LINK);
                });

        Optional.ofNullable(halObject.getObject())
                .ifPresent(x -> {
                    getNodes().stream()
                            .map(n -> doc.importNode(n, true))
                            .forEach(element::appendChild);
                });

        // links
        Optional.ofNullable(halObject.getHalLinks())
                .map(HalLinksXml::new)
                .map(l -> l.toXmlElements(doc))
                .orElse(new HashSet<>())
                .forEach(element::appendChild);

        // embedded
        Optional.ofNullable(halObject.getEmbedded())
                .map(HalEmbeddedXml::new)
                .map(e -> e.toXmlElements(doc))
                .orElse(new HashSet<>())
                .forEach(element::appendChild);

        return element;
    }

    /**
     * Extracts xml nodes from an entity object.
     *
     * @return The nodes.
     */
    private List<Node> getNodes() {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        DocumentBuilder db;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        Document doc = db.newDocument();

        // generic
        Object entity = Optional.of(halObject.getObject())
                .map(this::extractIfWrapper)
                .get();

        try {
            JAXBContext jc = getJAXBContext(halObject.getObject());

            Marshaller marshaller = jc.createMarshaller();
            marshaller.marshal(entity, doc);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        List<Node> nodeList = new ArrayList<>();
        NodeList nodes = doc.getDocumentElement().getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            nodeList.add(nodes.item(i));
        }

        return Collections.unmodifiableList(nodeList);
    }

    /**
     * Extract an object if it is wrapped into a {@link GenericEntityWrapper}.
     *
     * @param object The object which might be wrapped into
     *               a {@link GenericEntityWrapper}.
     * @return The unwrapped object
     */
    private Object extractIfWrapper(Object object) {
        return Optional.of(object)
                .filter(o -> o instanceof GenericEntityWrapper)
                .map(o -> (GenericEntityWrapper) o)
                .map(GenericEntityWrapper::getEntity)
                .orElse(object);
    }

    /**
     * Provides {@link JAXBContext} depending on if an object is
     * wrapped into {@link GenericEntityWrapper}.
     *
     * @param object The object which might be wrapped into
     *               a {@link GenericEntityWrapper}
     * @return The instance of {@link JAXBContext}
     */
    private JAXBContext getJAXBContext(Object object) {
        return Optional.of(object)
                .filter(o -> o instanceof GenericEntityWrapper)
                .map(o -> (GenericEntityWrapper) o)
                .map(o -> {

                    List<Class<?>> classList = new ArrayList<>();
                    classList.add(o.getEntity().getClass());
                    classList.addAll(extractTypeClasses(o.getGenericType()));

                    try {
                        return JAXBContext.newInstance(
                                classList.toArray(new Class<?>[] {}));
                    } catch (JAXBException e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElseGet(() -> {
                    try {
                        return JAXBContext.newInstance(object.getClass());
                    } catch (JAXBException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    /**
     * Extracts classes from an actual type arguments if the provided
     * type of a generic is instance of {@link ParameterizedType}.
     *
     * @param genericType The generic type
     * @return The list of extracted classes
     */
    private List<Class<?>> extractTypeClasses(Type genericType) {
        return Optional.of(genericType)
                .filter(t -> t instanceof ParameterizedType)
                .map(t -> (ParameterizedType) t)
                .map(t -> Stream.of(t.getActualTypeArguments()))
                .orElse(Stream.empty())
                .map(t -> {
                    try {
                        return Class.forName(t.getTypeName());
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toUnmodifiableList());
    }
}
