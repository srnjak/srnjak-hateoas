package com.srnjak.hateoas;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

/**
 * Top class of hypermedia model.
 */
@Data
@NoArgsConstructor
public class HypermediaModel {

    /**
     * List of links
     */
    @Setter(AccessLevel.NONE)
    private List<Link> linkList = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param links The links to be added to the model
     */
    public HypermediaModel(Link... links) {
        addLinks(links);
    }

    /**
     * Constructor
     *
     * @param links The collection of links to be added to the model
     */
    public HypermediaModel(Collection<Link> links) {
        addLinks(links);
    }

    /**
     * Add links to the model
     *
     * @param links The links to be added
     */
    public void addLinks(Link... links) {
        addLinks(Arrays.asList(links));
    }

    /**
     * Add links to the model
     *
     * @param links The collection of links to be added
     */
    public void addLinks(Collection<Link> links) {
        linkList.addAll(links);
    }

    /**
     * Add link to the model
     *
     * @param link The link to be added to the model
     */
    public void addLink(Link link) {
        linkList.add(link);
    }

    /**
     * Add link to the model consideration of a condition.
     *
     * @param link The link to be added to the model
     * @param condition The condition, which decides if link will be
     *                  really added
     */
    public void addLink(Link link, boolean condition) {
        Optional.of(link)
                .filter(x -> condition)
                .ifPresent(linkList::add);
    }
}
