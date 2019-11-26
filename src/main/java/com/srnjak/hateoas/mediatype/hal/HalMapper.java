package com.srnjak.hateoas.mediatype.hal;

import com.srnjak.hateoas.*;

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
                .addCuries(getHalCuries(linksPerRel))
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

        return HalObject.builder(entityModel.getEntity())
                .addLinks(getHalLinkEntryList(linksPerRel))
                .addCuries(getHalCuries(linksPerRel))
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
                                IanaLinkRelation.ITEM.getValue()));

        return HalObject.builder()
                .addLinks(halLinkEntryList)
                .addCuries(getHalCuries(linksPerRel))
                .addEmbedded(HalEmbedded.builder().add(itemEmbedded).build())
                .build();
    }

    /**
     * Gets {@link HalCuries} object from map of {@link Link} objects list
     * grouped by {@link LinkRelation}.
     *
     * @param linksPerRel The map of {@link Link} objects list
     *                    grouped by {@link LinkRelation}
     * @return The {@link HalCuries} object
     */
    private static HalCuries getHalCuries(
            Map<LinkRelation, List<Link>> linksPerRel) {

        return linksPerRel.keySet().stream()
                .map(LinkRelation::getRelationType)
                .filter(r -> r instanceof CurieRelationType)
                .map(r -> (CurieRelationType) r)
                .map(HalMapper::toHalCurie)
                .collect(HalCuries.collector());
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
                .map(r -> {

                    List<Link> linkList = linksPerRel.get(r);

                    String rel = Optional.ofNullable(
                            r.getRelationType().prefix())
                            .map(rt -> rt + ":" + r.getValue())
                            .orElse(r.getValue());

                    Stream<HalLink> halLinkStream = linkList.stream()
                            .map(HalMapper::toHalLink);

                    HalLinkEntry halLinkEntry;
                    if (linkList.size() > 1) {
                        halLinkEntry = halLinkStream.collect(
                                HalLinkListEntry.collector(rel));
                    } else {
                        halLinkEntry = halLinkStream.findAny()
                                .map(l -> HalLinkObjectEntry.builder()
                                        .rel(rel)
                                        .setLink(l)
                                        .build())
                                .orElseThrow();
                    }

                    return halLinkEntry;
                })
                .collect(Collectors.toList());
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
            HypermediaModel entityModel) {
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

    /**
     * Maps a {@link CurieRelationType} object into a {@link HalCurie} object.
     *
     * @param curieRelationType The {@link CurieRelationType} object
     * @return The {@link HalCurie} object
     */
    public static HalCurie toHalCurie(CurieRelationType curieRelationType) {
        return HalCurie.builder()
                .name(curieRelationType.getPrefix())
                .href(curieRelationType.getReference().getHref())
                .templated(curieRelationType.getReference().getTemplated())
                .build();
    }

}
