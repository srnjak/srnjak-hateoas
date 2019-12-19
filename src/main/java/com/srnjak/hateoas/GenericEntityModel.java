package com.srnjak.hateoas;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Entity type of hypermedia model. It wraps an entity into the hateoas context.
 *
 * @param <T> The type of the entity.
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GenericEntityModel<T> extends EntityModel<T> {

    private Type genericType;

    /**
     * Constructor.
     *
     * @param entity The entity to be wrapped
     */
    public GenericEntityModel(T entity, Type genericType) {
        super(entity);
        this.genericType = genericType;
    }

    /**
     * Constructor.
     *
     * @param entity The entity to be wrapped
     * @param links The links to be added to the model
     */
    public GenericEntityModel(T entity, Type genericType, Link... links) {
        super(entity, links);
        this.genericType = genericType;
    }

    /**
     * Constructor.
     *
     * @param entity The entity to be wrapped
     * @param links The collection of links to be added to the model
     */
    public GenericEntityModel(
            T entity, Type genericType, Collection<Link> links) {
        super(entity, links);
        this.genericType = genericType;
    }
}
