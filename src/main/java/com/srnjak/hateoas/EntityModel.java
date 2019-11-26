package com.srnjak.hateoas;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;

/**
 * Entity type of hypermedia model. It wraps an entity into the hateoas context.
 *
 * @param <T> The type of the entity.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EntityModel<T> extends HypermediaModel {

    /**
     * The wrapped entity of the model.
     */
    private T entity;

    /**
     * Constructor.
     *
     * @param entity The entity to be wrapped
     */
    public EntityModel(T entity) {
        this.entity = entity;
    }

    /**
     * Constructor.
     *
     * @param entity The entity to be wrapped
     * @param links The links to be added to the model
     */
    public EntityModel(T entity, Link... links) {
        super(links);
        this.entity = entity;
    }

    /**
     * Constructor.
     *
     * @param entity The entity to be wrapped
     * @param links The collection of links to be added to the model
     */
    public EntityModel(T entity, Collection<Link> links) {
        super(links);
        this.entity = entity;
    }
}
