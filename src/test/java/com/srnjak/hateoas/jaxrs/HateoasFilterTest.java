package com.srnjak.hateoas.jaxrs;

import com.srnjak.hateoas.EntityModel;
import com.srnjak.hateoas.relation.IanaLinkRelation;
import com.srnjak.hateoas.Link;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.Request;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HateoasFilterTest {

    @Data
    public static class TestEntity {
        private String a;
        private Integer b;
        private boolean c;
    }

    @Mock
    ContainerRequestContext requestContext;

    @Mock
    Request request;

    @Spy
    ContainerResponseContext responseContext;

    @BeforeEach
    public void prepareMocks() {
        lenient().when(requestContext.getRequest()).thenReturn(request);
    }

    @Test
    public void filter_WhenSupportedVariant() {
        when(request.selectVariant(HateoasVariants.SUPPORTED_VARIANTS))
                .thenReturn(HateoasVariants.VARIANT_APPLICATION_HAL_JSON);

        HateoasFilter tut = new HateoasFilter();

        tut.filter(requestContext, responseContext);

        verify(responseContext, never()).setEntity(any());
    }

    @Test
    public void filter_WhenUnsupportedVariant() {
        EntityModel<TestEntity> entityModel = getEntityModel();

        when(request.selectVariant(HateoasVariants.SUPPORTED_VARIANTS))
                .thenReturn(null);
        when(responseContext.getEntity()).thenReturn(entityModel);

        HateoasFilter tut = new HateoasFilter();

        tut.filter(requestContext, responseContext);

        verify(responseContext, times(1)).setEntity(entityModel.getEntity());
    }

    @Test
    public void filter_WhenUnsupportedVariant_NotHypermediaModel() {
        when(request.selectVariant(HateoasVariants.SUPPORTED_VARIANTS))
                .thenReturn(null);
        when(responseContext.getEntity()).thenReturn(new Object());

        HateoasFilter tut = new HateoasFilter();

        tut.filter(requestContext, responseContext);

        verify(responseContext, never()).setEntity(any());
    }

    private EntityModel<TestEntity> getEntityModel() {
        return new EntityModel<>(getTestEntity(), getLink());
    }

    private Link getLink() {
        return Link.builder()
                    .relation(IanaLinkRelation.SELF)
                    .href("http://www.example.com")
                    .build();
    }

    private TestEntity getTestEntity() {
        TestEntity entity = new TestEntity();
        entity.setA("x");
        entity.setB(12);
        entity.setC(true);
        return entity;
    }

}