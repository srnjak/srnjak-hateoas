package com.srnjak.hateoas;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Collection type of hypermedia model. It wraps a collection of entities into
 * the hateoas context.
 *
 * @param <E>
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CollectionModel<E> extends HypermediaModel {

    /**
     * The list of entities of the model
     */
    private List<EntityModel<E>> content = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param entities The collection of entities to be wrapped
     */
    public CollectionModel(Collection<EntityModel<E>> entities) {
        this.content.addAll(entities);
    }

    /**
     * Constructor.
     *
     * @param entities The collection of entities to be wrapped
     * @param links The links to be added to the model
     */
    public CollectionModel(Collection<EntityModel<E>> entities, Link... links) {
        super(links);
        this.content.addAll(entities);
    }

    /**
     * Constructor.
     *
     * @param entities The collection of entities to be wrapped
     * @param links The collection of links to be added to the model
     */
    public CollectionModel(Collection<EntityModel<E>> entities, Collection<Link> links) {
        super(links);
        this.content.addAll(entities);
    }

    /**
     * Adds entity to the collection
     *
     * @param entity The entity to be added
     */
    public void add(EntityModel<E> entity) {
        this.content.add(entity);
    }
}
