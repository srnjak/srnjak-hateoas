package com.srnjak.hateoas.mediatype.hal.xml;

import com.srnjak.hateoas.IanaLinkRelation;
import com.srnjak.hateoas.mediatype.hal.HalLinkEntry;
import com.srnjak.hateoas.mediatype.hal.HalLinkObjectEntry;
import com.srnjak.hateoas.mediatype.hal.HalObject;
import com.srnjak.hateoas.utils.GenericEntityWrapper;
import lombok.AllArgsConstructor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
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

    private HalObject halObject;

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
                toResourceElement(doc, IanaLinkRelation.SELF.getValue());

        // TODO curie
        rootResource.setAttributeNS(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, "xmlns:pref", "http://blabla");

        doc.appendChild(rootResource);

        return doc;
    }

    public Element toResourceElement(Document doc, String rel) {
        Element element = doc.createElement("resource");

        halObject.getHalLinks().get(IanaLinkRelation.SELF.getValue())
                .filter(e -> e instanceof HalLinkObjectEntry)
                .map(e -> (HalLinkObjectEntry) e)
                .map(HalLinkObjectEntry::getHalLink)
                .ifPresent(l -> {
                    element.setAttribute("rel", rel);
                    HalLinksXml.setLinkAttributesToElement(element, l);
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
                .get()
                .forEach(element::appendChild);

        // embedded
        Optional.ofNullable(halObject.getEmbedded())
                .map(HalEmbeddedXml::new)
                .map(e -> e.toXmlElements(doc))
                .orElse(new HashSet<>())
                .forEach(element::appendChild);

        return element;
    }

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

    private Object extractIfWrapper(Object object) {
        return Optional.of(object)
                .filter(o -> o instanceof GenericEntityWrapper)
                .map(o -> (GenericEntityWrapper) o)
                .map(GenericEntityWrapper::getEntity)
                .orElse(object);
    }

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
