package com.srnjak.hateoas;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
import java.util.function.Predicate;

/**
 *
 */
@Data
@NoArgsConstructor
public class HypermediaModel {

    @Setter(AccessLevel.NONE)
    private List<Link> linkList = new ArrayList<>();

    public HypermediaModel(Link... links) {
        addLinks(links);
    }

    public HypermediaModel(Collection<Link> links) {
        addLinks(links);
    }

    public void addLinks(Link... links) {
        addLinks(Arrays.asList(links));
    }

    public void addLinks(Collection<Link> links) {
        linkList.addAll(links);
    }

    public void addLink(Link link) {
        linkList.add(link);
    }

    public void addLink(Link link, boolean condition) {
        Optional.of(link)
                .filter(x -> condition)
                .ifPresent(linkList::add);
    }
}
