package com.srnjak.hateoas.jaxrs.model;

import com.srnjak.hateoas.GenericEntityModel;
import com.srnjak.hateoas.Link;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.ws.rs.core.GenericType;
import java.util.Collection;

/**
 * Entity type of hypermedia model. It wraps an entity into the hateoas context.
 *
 * @param <T> The type of the entity.
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GenericJaxrsEntityModel<T> extends GenericEntityModel<T> {

    /**
     * Constructor.
     *
     * @param entity The entity to be wrapped
     */
    public GenericJaxrsEntityModel(T entity, GenericType<T> genericType) {
        super(entity, genericType.getType());
    }

    /**
     * Constructor.
     *
     * @param entity The entity to be wrapped
     * @param links The links to be added to the model
     */
    public GenericJaxrsEntityModel(
            T entity, GenericType<T> genericType, Link... links) {
        super(entity, genericType.getType(), links);
    }

    /**
     * Constructor.
     *
     * @param entity The entity to be wrapped
     * @param links The collection of links to be added to the model
     */
    public GenericJaxrsEntityModel(
            T entity, GenericType<T> genericType, Collection<Link> links) {
        super(entity, genericType.getType(), links);
    }
}
