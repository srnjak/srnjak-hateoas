package com.srnjak.hateoas.mediatype.hal;

import com.srnjak.hateoas.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HalMapper {

    public static HalObject toHalObject(HypermediaModel hypermediaModel) {

        Map<LinkRelation, List<Link>> linksPerRel =
                getLinksPerRel(hypermediaModel);

        return HalObject.builder()
                .addLinks(getHalLinkEntryList(linksPerRel))
                .addCuries(getHalCuries(linksPerRel))
                .build();
    }

    public static HalObject toHalObject(EntityModel<?> entityModel) {

        Map<LinkRelation, List<Link>> linksPerRel = getLinksPerRel(entityModel);

        return HalObject.builder(entityModel.getContent())
                .addLinks(getHalLinkEntryList(linksPerRel))
                .addCuries(getHalCuries(linksPerRel))
                .build();
    }

    public static HalObject toHalObject(CollectionModel<?> collectionModel) {

        Map<LinkRelation, List<Link>> linksPerRel =
                getLinksPerRel(collectionModel);

        List<HalLinkEntry> halLinkEntryList = getHalLinkEntryList(linksPerRel);

//        HalLinkEntry itemLinkEntry = collectionModel.getEntityList().stream()
//                .map(o -> o.getLinkList().stream()
//                        .filter(l -> l.getRelation()
//                                .equals(IanaLinkRelation.SELF))
//                        .findAny()
//                        .orElseThrow())
//                .map(HalMapper::toHalLink)
//                .collect(HalLinkEntry.collector(
//                        IanaLinkRelation.ITEM.getValue()));
//        halLinkEntryList.add(itemLinkEntry);

        HalEmbeddedEntry itemEmbedded = collectionModel.getContent().stream()
                .map(HalMapper::toHalObject)
                .collect(HalEmbeddedEntry.collector(
                        IanaLinkRelation.ITEM.getValue()));

        return HalObject.builder()
                .addLinks(halLinkEntryList)
                .addCuries(getHalCuries(linksPerRel))
                .addEmbedded(HalEmbedded.builder().put(itemEmbedded).build())
                .build();
    }

    private static HalCuries getHalCuries(
            Map<LinkRelation, List<Link>> linksPerRel) {

        return linksPerRel.keySet().stream()
                .map(r -> r.getRelationType())
                .filter(r -> r instanceof CurieRelationType)
                .map(r -> (CurieRelationType) r)
                .map(HalMapper::toHalCurie)
                .collect(HalCuries.collector());
    }

    private static List<HalLinkEntry> getHalLinkEntryList(
            Map<LinkRelation, List<Link>> linksPerRel) {

        return linksPerRel.keySet().stream()
                .map(r -> {

                    List<Link> linkList = linksPerRel.get(r);

                    String rel = Optional.ofNullable(r.getRelationType().name())
                            .map(rt -> rt + ":" + r.getValue())
                            .orElse(r.getValue());

                    Stream<HalLink> halLinkStream = linkList.stream()
                            .map(HalMapper::toHalLink);

                    HalLinkEntry halLinkEntry;
                    if (linkList.size() > 1) {
                        halLinkEntry = halLinkStream.collect(
                                HalLinkEntry.collector(rel));
                    } else {
                        halLinkEntry = halLinkStream.findAny()
                                .map(l -> HalLinkEntry.builder()
                                        .rel(rel)
                                        .addLink(l)
                                        .buildSingle())
                                .orElseThrow();
                    }

                    return halLinkEntry;
                })
                .collect(Collectors.toList());
    }

    private static Map<LinkRelation, List<Link>> getLinksPerRel(
            HypermediaModel entityModel) {
        return entityModel.getLinkList().stream()
                .collect(Collectors.groupingBy(Link::getRelation));
    }

    public static HalLink toHalLink(Link l) {
        return HalLink.builder()
                .href(String.valueOf(l.getHref()))
                .templated(l.getTemplated())
                .type(l.getType())
                .deprecation(l.getDeprecation())
                .name(l.getName())
                .profile(l.getProfile())
                .title(l.getTitle())
                .hreflang(l.getHreflang()).build();
    }

    public static HalCurie toHalCurie(CurieRelationType r) {
        return HalCurie.builder()
                .name(r.getName())
                .href(r.getTarget().getHref())
                .templated(r.getTarget().getTemplated())
                .build();
    }

}
