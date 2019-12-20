package com.srnjak.hateoas.mediatype.hal;

import com.srnjak.hateoas.*;
import com.srnjak.hateoas.relation.*;
import com.srnjak.hateoas.utils.GenericEntityWrapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utilities for mapping from a hateoas models to a hal object.
 */
public class HalMapper {

    /**
     * Maps a {@link HypermediaModel} type of object into
     * a {@link HalObject} object.
     *
     * @param hypermediaModel The {@link HypermediaModel} type of object
     * @return The {@link HalObject} object
     */
    public static HalObject toHalObject(HypermediaModel hypermediaModel) {

        Map<LinkRelation, List<Link>> linksPerRel =
                getLinksPerRel(hypermediaModel);

        return HalObject.builder()
                .addLinks(getHalLinkEntryList(linksPerRel))
                .build();
    }

    /**
     * Maps a {@link EntityModel} type of object into
     * a {@link HalObject} object.
     *
     * @param entityModel The {@link EntityModel} type of object
     * @return The {@link HalObject} object
     */
    public static HalObject toHalObject(EntityModel<?> entityModel) {

        Map<LinkRelation, List<Link>> linksPerRel = getLinksPerRel(entityModel);

        // generics
        Object entity = Optional.of(entityModel)
                .filter(e -> e instanceof  GenericEntityModel)
                .map(e -> (GenericEntityModel<?>) e)
                .map(e -> new GenericEntityWrapper(
                        e.getEntity(), e.getGenericType()))
                .map(e -> (Object) e)
                .orElse(entityModel.getEntity());

        return HalObject.builder(entity)
                .addLinks(getHalLinkEntryList(linksPerRel))
                .build();
    }

    /**
     * Maps a {@link CollectionModel} type of object into
     * a {@link HalObject} object.
     *
     * @param collectionModel The {@link CollectionModel} type of object
     * @return The {@link HalObject} object
     */
    public static HalObject toHalObject(CollectionModel<?> collectionModel) {

        Map<LinkRelation, List<Link>> linksPerRel =
                getLinksPerRel(collectionModel);

        List<HalLinkEntry> halLinkEntryList = getHalLinkEntryList(linksPerRel);

//        HalLinkEntry itemLinkEntry = collectionModel.getContent().stream()
//                .map(o -> o.getLinkList().stream()
//                        .filter(l -> l.getRelation()
//                                .equals(IanaLinkRelation.SELF))
//                        .findAny()
//                        .orElseThrow())
//                .map(HalMapper::toHalLink)
//                .collect(HalLinkListEntry.collector(
//                        IanaLinkRelation.ITEM.getValue()));
//        halLinkEntryList.add(itemLinkEntry);

        HalEmbeddedListEntry itemEmbedded =
                collectionModel.getContent().stream()
                        .map(HalMapper::toHalObject)
                        .collect(HalEmbeddedListEntry.collector(
                                IanaLinkRelation.ITEM));

        return HalObject.builder()
                .addLinks(halLinkEntryList)
                .addEmbedded(HalEmbedded.builder().add(itemEmbedded).build())
                .build();
    }

    /**
     * Gets a list of {@link HalLinkEntry} objects from map of
     * {@link Link} objects list grouped by {@link LinkRelation}.
     *
     * @param linksPerRel The map of {@link Link} objects list
     *                    grouped by {@link LinkRelation}
     * @return The list of {@link HalLinkEntry} objects
     */
    private static List<HalLinkEntry> getHalLinkEntryList(
            Map<LinkRelation, List<Link>> linksPerRel) {

        return linksPerRel.keySet().stream()
                .peek(HalMapper::verifyLinkRelation)
                .map(r -> {

                    List<Link> linkList = linksPerRel.get(r);

                    Stream<HalLink> halLinkStream = linkList.stream()
                            .map(HalMapper::toHalLink);

                    HalLinkEntry halLinkEntry;
                    if (linkList.size() > 1) {
                        halLinkEntry = halLinkStream.collect(
                                HalLinkListEntry.collector(r));
                    } else {
                        halLinkEntry = halLinkStream.findAny()
                                .map(l -> HalLinkObjectEntry.builder()
                                        .rel(r)
                                        .setLink(l)
                                        .build())
                                .orElseThrow();
                    }

                    return halLinkEntry;
                })
                .collect(Collectors.toList());
    }

    /**
     * Verifies if link relation is instance of a supported type.
     *
     * @param linkRelation The link relation
     */
    private static void verifyLinkRelation(LinkRelation linkRelation) {
        Optional.of(linkRelation)
                .filter(l -> l instanceof IanaLinkRelation
                        || l instanceof CustomRelation)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unsupported link relation type: "
                                + linkRelation.getClass().getName()));
    }

    /**
     * Creates map of list of {@link Link} objects grouped by
     * {@link LinkRelation} from an {@link EntityModel} object.
     *
     * @param entityModel The {@link EntityModel} object
     * @return The map of list of {@link Link} objects
     *          grouped by {@link LinkRelation}
     */
    private static Map<LinkRelation, List<Link>> getLinksPerRel(
            HypermediaModel<?> entityModel) {

        return entityModel.getLinkList().stream()
                .collect(Collectors.groupingBy(Link::getRelation));
    }

    /**
     * Maps a {@link Link} object into a {@link HalLink} object.
     *
     * @param link The {@link Link} object
     * @return The {@link HalLink} object
     */
    public static HalLink toHalLink(Link link) {
        return HalLink.builder()
                .href(link.getHref())
                .templated(link.getTemplated())
                .type(link.getType())
                .deprecation(link.getDeprecation())
                .name(link.getName())
                .profile(link.getProfile())
                .title(link.getTitle())
                .hreflang(link.getHreflang()).build();
    }

}
