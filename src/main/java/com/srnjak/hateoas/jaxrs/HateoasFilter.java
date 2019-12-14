package com.srnjak.hateoas.jaxrs;

import com.srnjak.hateoas.CollectionModel;
import com.srnjak.hateoas.EntityModel;
import com.srnjak.hateoas.HypermediaModel;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Variant;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * JAX-RS response filter which extracts an entity from a
 * {@link HypermediaModel} object, if client requests for application/json
 * format.
 */
@Provider
public class HateoasFilter implements ContainerResponseFilter {

    /**
     * {@inheritDoc}
     */
    @Override
    public void filter(
            ContainerRequestContext requestCtx,
            ContainerResponseContext responseCtx) {

        Variant variant = requestCtx.getRequest().selectVariant(
                HateoasVariants.SUPPORTED_VARIANTS);

        if (variant == null) {
            Optional.ofNullable(responseCtx.getEntity())
                    .filter(e -> e instanceof HypermediaModel)
                    .map(e -> (HypermediaModel) e)
                    .ifPresent(e -> responseCtx.setEntity(getEntity(e)));
        }
    }

    /**
     * Extracts entity from hypermedia model
     *
     * @param hypermediaModel The hypermedia model
     * @return The extracted entity
     */
    private Object getEntity(HypermediaModel hypermediaModel) {

        Optional<Object> e1 = Optional.of(hypermediaModel)
                .filter(h -> h instanceof EntityModel)
                .map(h -> (EntityModel<?>) h)
                .map(this::getEntity);

        Optional<Object> e2 = Optional.of(hypermediaModel)
                .filter(h -> h instanceof CollectionModel)
                .map(h -> (CollectionModel<?>) h)
                .map(this::getEntity);

        return e1.orElse(e2.orElse(null));
    }

    /**
     * Extracts entity from entity model
     *
     * @param entityModel The entity model
     * @return The extracted entity
     */
    private Object getEntity(EntityModel<?> entityModel) {
        return entityModel.getEntity();
    }

    /**
     * Extracts collection of entities from collection model
     *
     * @param collectionModel The collection model
     * @return The list of extracted entities
     */
    private List<?> getEntity(CollectionModel<?> collectionModel) {
        return  collectionModel.getContent().stream()
                .map(EntityModel::getEntity)
                .collect(Collectors.toList());
    }

}
