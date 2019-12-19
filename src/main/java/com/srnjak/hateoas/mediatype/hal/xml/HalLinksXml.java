package com.srnjak.hateoas.mediatype.hal.xml;

import com.srnjak.hateoas.IanaLinkRelation;
import com.srnjak.hateoas.mediatype.hal.*;
import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
import lombok.AllArgsConstructor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.xml.bind.JAXBContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * A xml serializer of a {@link HalLink} object.
 */
@AllArgsConstructor
public class HalLinksXml {

    public static final String LINK_ELEMENT = "link";
    public static final String REL_ATTRIBUTE = "rel";

    private HalLinks halLinks;

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

    private Set<Element> fromHalLinkObjectEntry(
            Document document, HalLinkEntry entry) {

        Element element = Optional.of(entry)
                .filter(e -> e instanceof HalLinkObjectEntry)
                .map(e -> (HalLinkObjectEntry) e)
                .map(e -> toElement(document, e.getRel(), e.getHalLink()))
                .orElseThrow();

        return Set.of(element);
    }

    private Set<Element> fromHalLinkListEntry(
            Document document, HalLinkEntry entry) {

        return Optional.of(entry)
                .filter(e -> e instanceof HalLinkListEntry)
                .map(e -> (HalLinkListEntry) e)
                .map(HalLinkListEntry::getHalLinkList)
                .map(list -> list.stream()
                        .map(l -> toElement(document, entry.getRel(), l))
                        .collect(Collectors.toUnmodifiableSet()))
                .orElseThrow();
    }

    private Element toElement(
            Document document,
            String rel,
            HalLink halLink) {

        Element linkElement = document.createElement(LINK_ELEMENT);
        linkElement.setAttribute(REL_ATTRIBUTE, rel);

        setLinkAttributesToElement(linkElement, halLink);
        return linkElement;
    }

    public static void setLinkAttributesToElement(
            Element element, HalLink halLink) {

        Optional.ofNullable(halLink.getHref())
                .ifPresent(h -> element.setAttribute(
                        HalLink.Fields.href, h));
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
