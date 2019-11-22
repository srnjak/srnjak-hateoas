package com.srnjak.hateoas.jaxrs;

import com.srnjak.hateoas.CollectionModel;
import com.srnjak.hateoas.EntityModel;
import com.srnjak.hateoas.HypermediaModel;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Variant;
import javax.ws.rs.ext.Provider;
import java.util.Optional;
import java.util.stream.Collectors;

@Provider
public class HateoasFilter implements ContainerResponseFilter {

    @Override
    public void filter(
            ContainerRequestContext requestCtx,
            ContainerResponseContext responseCtx) {

        Variant variant = requestCtx.getRequest().selectVariant(
                HateoasVariants.SUPPORTED_VARIANTS);

        Optional.ofNullable(variant)
                .filter(v -> v.equals(HateoasVariants.VARIANT_APPLICATION_JSON))
                .flatMap(v -> Optional.ofNullable(responseCtx.getEntity())
                        .filter(e -> e instanceof HypermediaModel)
                        .map(e -> (HypermediaModel) e))
                .ifPresent(e -> responseCtx.setEntity(getEntity(e)));
    }

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

    private Object getEntity(EntityModel<?> entityModel) {
        return entityModel.getContent();
    }

    private Object getEntity(CollectionModel<?> collectionModel) {
        return  collectionModel.getContent().stream()
                .map(EntityModel::getContent)
                .collect(Collectors.toList());
    }

}
