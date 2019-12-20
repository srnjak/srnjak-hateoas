package com.srnjak.hateoas.mediatype.hal;

import com.srnjak.hateoas.LinkRelation;
import com.srnjak.hateoas.relation.CurieDefinition;
import com.srnjak.hateoas.relation.CurieLinkRelation;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility for extracting curie type of relations from a hal objects tree.
 */
@AllArgsConstructor
public class CurieExtractor {

    /**
     * The hal representation.
     */
    private HalObject halObject;

    /**
     * Extracts curie type of relations from a hal objects tree.
     *
     * @return The set of {@link CurieDefinition} instances.
     */
    public Set<CurieDefinition> extract() {
        Set<CurieDefinition> curieDefinitions = new HashSet<>();
        curieDefinitions.addAll(extractFromObject());
        curieDefinitions.addAll(extractFromEmbedded());

        return Collections.unmodifiableSet(curieDefinitions);
    }

    /**
     * Extracts curie type of relations from a root object.
     *
     * @return The set of {@link CurieDefinition} instances.
     */
    private Set<CurieDefinition> extractFromObject() {
        Stream<LinkRelation> relFromLinks =
                Optional.ofNullable(halObject.getHalLinks())
                        .stream()
                        .map(HalLinks::getEntrySet)
                        .flatMap(Collection::stream)
                        .map(HalLinkEntry::getRel);

        Stream<LinkRelation> relFromEmbedded =
                Optional.ofNullable(halObject.getEmbedded())
                        .stream()
                        .map(HalEmbedded::getEntrySet)
                        .flatMap(Collection::stream)
                        .map(HalEmbeddedEntry::getRel);

        return Stream.concat(relFromLinks, relFromEmbedded)
                .filter(r -> r instanceof CurieLinkRelation)
                .map(r -> (CurieLinkRelation) r)
                .map(CurieLinkRelation::getCurie)
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Extracts curie type of relations from an embedded resource object.
     *
     * @return The set of {@link CurieDefinition} instances.
     */
    private Set<CurieDefinition> extractFromEmbedded() {
        List<HalEmbeddedEntry> embeddedEntries =
                Optional.ofNullable(halObject.getEmbedded())
                        .map(HalEmbedded::getEntrySet)
                        .stream()
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());

        Stream<CurieDefinition> fromListEntry = embeddedEntries.stream()
                .filter(e -> e instanceof HalEmbeddedListEntry)
                .map(e -> (HalEmbeddedListEntry) e)
                .map(HalEmbeddedListEntry::getHalObjectList)
                .flatMap(Collection::stream)
                .map(CurieExtractor::new)
                .map(CurieExtractor::extract)
                .flatMap(Collection::stream);

        Stream<CurieDefinition> fromObjectEntry = embeddedEntries.stream()
                .filter(e -> e instanceof HalEmbeddedObjectEntry)
                .map(e -> (HalEmbeddedObjectEntry) e)
                .map(HalEmbeddedObjectEntry::getHalObject)
                .map(CurieExtractor::new)
                .map(CurieExtractor::extract)
                .flatMap(Collection::stream);

        return Stream.concat(fromListEntry, fromObjectEntry)
                .collect(Collectors.toUnmodifiableSet());
    }
}
