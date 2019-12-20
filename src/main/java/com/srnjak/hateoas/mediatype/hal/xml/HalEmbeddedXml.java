package com.srnjak.hateoas.mediatype.hal.xml;

import com.srnjak.hateoas.relation.IanaLinkRelation;
import com.srnjak.hateoas.mediatype.hal.*;
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
public class HalEmbeddedXml {

    /**
     * The "rel" attribute name
     */
    public static final String REL_ATTRIBUTE = "rel";

    /**
     * The embedded hal resource
     */
    private HalEmbedded halEmbedded;

    /**
     * Transforms into a set of xml elements for hal representation of
     * an embedded resource.
     *
     * @param document The document where element will belong to
     * @return The set of xml elements
     */
    public Set<Element> toXmlElements(Document document) {

        Map<Class<? extends HalEmbeddedEntry>,
                BiFunction<Document, HalEmbeddedEntry,
                Set<Element>>> functionMap = new HashMap<>();

        functionMap.put(
                HalEmbeddedObjectEntry.class, this::fromHalEmbeddedObjectEntry);
        functionMap.put(
                HalEmbeddedListEntry.class, this::fromHalEmbeddedListEntry);

        return halEmbedded.getEntrySet().stream()
                .filter(e -> !e.getRel().equals(
                        IanaLinkRelation.SELF.getValue()))
                .flatMap(e -> functionMap.get(e.getClass())
                        .apply(document, e).stream())
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Transforms into a set of xml elements for a single object from
     * a {@link HalEmbeddedObjectEntry} instance.
     *
     * @param document The document where elements will belong to
     * @param entry The instance of a {@link HalEmbeddedObjectEntry}
     * @return The set of xml elements
     */
    private Set<Element> fromHalEmbeddedObjectEntry(
            Document document, HalEmbeddedEntry entry) {

        Element element = Optional.of(entry)
                .filter(e -> e instanceof HalEmbeddedObjectEntry)
                .map(e -> (HalEmbeddedObjectEntry) e)
                .map(HalEmbeddedObjectEntry::getHalObject)
                .map(HalObjectXml::new)
                .map(hox -> hox.toResourceElement(document, entry.getRel()))
                .orElseThrow();

        return Set.of(element);
    }

    /**
     * Transforms into a set of xml elements for a list of objects from
     * a {@link HalEmbeddedListEntry} instance.
     *
     * @param document The document where elements will belong to
     * @param entry The instance of a {@link HalEmbeddedListEntry}
     * @return The set of xml elements
     */
    private Set<Element> fromHalEmbeddedListEntry(
            Document document, HalEmbeddedEntry entry) {

        return Optional.of(entry)
                .filter(e -> e instanceof HalEmbeddedListEntry)
                .map(e -> (HalEmbeddedListEntry) e)
                .map(HalEmbeddedListEntry::getHalObjectList)
                .map(list -> list.stream()
                        .map(HalObjectXml::new)
                        .map(hox -> hox.toResourceElement(
                                document, entry.getRel()))
                        .collect(Collectors.toUnmodifiableSet()))
                .orElseThrow();
    }
}
