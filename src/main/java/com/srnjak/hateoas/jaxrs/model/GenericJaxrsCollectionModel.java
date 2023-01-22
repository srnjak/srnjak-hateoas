package com.srnjak.hateoas.jaxrs.model;

import com.srnjak.hateoas.EntityModel;
import com.srnjak.hateoas.GenericCollectionModel;
import com.srnjak.hateoas.Link;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import jakarta.ws.rs.core.GenericType;
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
public class GenericJaxrsCollectionModel<E> extends GenericCollectionModel<E> {

    public GenericJaxrsCollectionModel(
            Collection<EntityModel<E>> entities,
            GenericType<List<E>> genericType) {
        super(entities, genericType.getType());
    }

    /**
     * Constructor.
     *
     * @param entities The collection of entities to be wrapped
     * @param links The links to be added to the model
     */
    public GenericJaxrsCollectionModel(
            Collection<EntityModel<E>> entities,
            GenericType<List<E>> genericType,
            Link... links) {

        super(entities, genericType.getType(), links);
    }

    /**
     * Constructor.
     *
     * @param entities The collection of entities to be wrapped
     * @param links The collection of links to be added to the model
     */
    public GenericJaxrsCollectionModel(
            Collection<EntityModel<E>> entities,
            GenericType<List<E>> genericType,
            Collection<Link> links) {

        super(entities, genericType.getType(), links);
    }
}
