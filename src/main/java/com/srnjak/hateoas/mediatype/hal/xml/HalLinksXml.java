package com.srnjak.hateoas.mediatype.hal.xml;

import com.srnjak.hateoas.mediatype.hal.*;
import com.srnjak.hateoas.relation.IanaLinkRelation;
import lombok.AllArgsConstructor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * A xml serializer of a {@link HalLink} object.
 */
@AllArgsConstructor
public class HalLinksXml {

    /**
     * The name of a "link" element.
     */
    public static final String LINK_ELEMENT = "link";

    /**
     * The name of a "rel" attribute.
     */
    public static final String REL_ATTRIBUTE = "rel";

    /**
     * The hal representation of a set of links.
     */
    private HalLinks halLinks;

    /**
     * Transforms into a set of xml elements for hal representation of
     * a set of links.
     *
     * @param document The document where elements will belong to
     * @return The set of xml elements
     */
    public Set<Element> toXmlElements(Document document) {

        Map<Class<? extends HalLinkEntry>,
                BiFunction<Document, HalLinkEntry,
                Set<Element>>> functionMap = new HashMap<>();

        functionMap.put(HalLinkObjectEntry.class, this::fromHalLinkObjectEntry);
        functionMap.put(HalLinkListEntry.class, this::fromHalLinkListEntry);

        return halLinks.getEntrySet().stream()
                .filter(e -> !e.getRel().equals(
                        IanaLinkRelation.SELF.getValue()))
                .flatMap(e -> functionMap.get(e.getClass())
                        .apply(document, e).stream())
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Transforms into a set of xml elements for a single object from
     * a {@link HalLinkObjectEntry} instance.
     *
     * @param document The document where elements will belong to
     * @param entry The instance of {@link HalLinkObjectEntry}
     * @return The set of xml elements
     */
    private Set<Element> fromHalLinkObjectEntry(
            Document document, HalLinkEntry entry) {

        Element element = Optional.of(entry)
                .filter(e -> e instanceof HalLinkObjectEntry)
                .map(e -> (HalLinkObjectEntry) e)
                .map(e -> toElement(
                        document, e.getRel().getValue(), e.getHalLink()))
                .orElseThrow();

        return Set.of(element);
    }

    /**
     * Transforms into a set of xml elements for a list of objects from
     * a {@link HalLinkListEntry} instance.
     *
     * @param document The document where elements will belong to
     * @param entry The instance of {@link HalLinkListEntry}
     * @return The set of xml elements
     */
    private Set<Element> fromHalLinkListEntry(
            Document document, HalLinkEntry entry) {

        return Optional.of(entry)
                .filter(e -> e instanceof HalLinkListEntry)
                .map(e -> (HalLinkListEntry) e)
                .map(HalLinkListEntry::getHalLinkList)
                .map(list -> list.stream()
                        .map(l -> toElement(
                                document, entry.getRel().getValue(), l))
                        .collect(Collectors.toUnmodifiableSet()))
                .orElseThrow();
    }

    /**
     * Transforms hal representation of a link into a xml element.
     *
     * @param document The document where element will belong to
     * @param rel The relation of the link
     * @param halLink The hal representation of a link
     * @return The xml element representing the link
     */
    private Element toElement(
            Document document,
            String rel,
            HalLink halLink) {

        Element linkElement = document.createElement(LINK_ELEMENT);
        linkElement.setAttribute(REL_ATTRIBUTE, rel);

        setLinkAttributesToElement(linkElement, halLink);
        return linkElement;
    }

    /**
     * Setts attributes of a hal representation of a link to a xml element.
     *
     * @param element The xml element
     * @param halLink The hal representation of a link
     */
    public static void setLinkAttributesToElement(
            Element element, HalLink halLink) {

        element.setAttribute(HalLink.Fields.href, halLink.getHref());

        Optional.ofNullable(halLink.getTemplated())
                .ifPresent(t -> element.setAttribute(
                        HalLink.Fields.templated, t.toString()));
        Optional.ofNullable(halLink.getType())
                .ifPresent(t -> element.setAttribute(
                        HalLink.Fields.type, t));
        Optional.ofNullable(halLink.getDeprecation())
                .ifPresent(d -> element.setAttribute(
                        HalLink.Fields.deprecation, d));
        Optional.ofNullable(halLink.getName())
                .ifPresent(n -> element.setAttribute(
                        HalLink.Fields.name, n));
        Optional.ofNullable(halLink.getProfile())
                .ifPresent(p -> element.setAttribute(
                        HalLink.Fields.profile, p));
        Optional.ofNullable(halLink.getTitle())
                .ifPresent(t -> element.setAttribute(
                        HalLink.Fields.title, t));
        Optional.ofNullable(halLink.getHreflang())
                .ifPresent(h -> element.setAttribute(
                        HalLink.Fields.hreflang, h));
    }
}
