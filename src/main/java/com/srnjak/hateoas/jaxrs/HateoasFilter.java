package com.srnjak.hateoas.jaxrs;

import com.srnjak.hateoas.*;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Variant;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
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

        Map<Class<? extends HypermediaModel>, Function<HypermediaModel, Object>>
                functionMap = new HashMap<>();
        functionMap.put(
                GenericEntityModel.class,
                m -> this.getEntity((GenericEntityModel<?>) m));
        functionMap.put(
                EntityModel.class,
                m -> this.getEntity((EntityModel<?>) m));
        functionMap.put(
                GenericCollectionModel.class,
                m -> this.getEntity((GenericCollectionModel<?>) m));
        functionMap.put(
                CollectionModel.class,
                m -> this.getEntity((CollectionModel<?>) m));
        functionMap.put(HypermediaModel.class, m -> null);


        Class<?> clazz = hypermediaModel.getClass();
        while (!functionMap.containsKey(clazz)) {
            clazz = clazz.getSuperclass();
        }

        return functionMap.get(clazz).apply(hypermediaModel);
    }

    /**
     * Extracts entity from entity model
     *
     * @param entityModel The entity model
     * @return The extracted entity
     */
    private <E> E getEntity(EntityModel<E> entityModel) {
        return entityModel.getEntity();
    }

    /**
     * Extracts entity from entity model
     *
     * @param genericEntityModel The entity model
     * @return The extracted entity
     */
    private <E> GenericEntity<E> getEntity(
            GenericEntityModel<E> genericEntityModel) {

        E entity = getEntity((EntityModel<E>) genericEntityModel);

        return new GenericEntity<>(entity, genericEntityModel.getGenericType());
    }

    /**
     * Extracts collection of entities from collection model
     *
     * @param collectionModel The collection model
     * @return The list of extracted entities
     */
    private <E> List<E> getEntity(CollectionModel<E> collectionModel) {

        return collectionModel.getContent().stream()
                .map(EntityModel::getEntity)
                .collect(Collectors.toList());
    }

    /**
     * Extracts collection of entities from collection model
     *
     * @param genericCollectionModel The collection model
     * @return The list of extracted entities
     */
    private <E> GenericEntity<List<E>> getEntity(
            GenericCollectionModel<E> genericCollectionModel) {

        List<E> list = getEntity((CollectionModel<E>) genericCollectionModel);

        return new GenericEntity<>(
                list, genericCollectionModel.getGenericType());
    }

}
