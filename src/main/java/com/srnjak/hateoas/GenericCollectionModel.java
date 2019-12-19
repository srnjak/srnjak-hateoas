package com.srnjak.hateoas;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Collection type of hypermedia model. It wraps a collection of entities into
 * the hateoas context.
 *
 * @param <E>
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GenericCollectionModel<E> extends CollectionModel<E> {

    private Type genericType;

    public GenericCollectionModel(
            Collection<EntityModel<E>> entities, Type genericType) {
        super(entities);
        this.genericType = genericType;
    }

    /**
     * Constructor.
     *
     * @param entities The collection of entities to be wrapped
     * @param links The links to be added to the model
     */
    public GenericCollectionModel(
            Collection<EntityModel<E>> entities,
            Type genericType,
            Link... links) {

        super(entities, links);
        this.genericType = genericType;
    }

    /**
     * Constructor.
     *
     * @param entities The collection of entities to be wrapped
     * @param links The collection of links to be added to the model
     */
    public GenericCollectionModel(
            Collection<EntityModel<E>> entities,
            Type genericType,
            Collection<Link> links) {

        super(entities, links);
        this.genericType = genericType;
    }
}
