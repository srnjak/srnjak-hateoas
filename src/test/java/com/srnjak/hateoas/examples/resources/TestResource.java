package com.srnjak.hateoas.examples.resources;

import com.srnjak.hateoas.*;
import com.srnjak.hateoas.examples.entities.TestEntity;
import com.srnjak.hateoas.examples.entities.TestGenericEntity;
import com.srnjak.hateoas.examples.relations.TestCurieRelation;
import com.srnjak.hateoas.jaxrs.model.GenericJaxrsCollectionModel;
import com.srnjak.hateoas.jaxrs.model.GenericJaxrsEntityModel;
import com.srnjak.hateoas.mediatype.hal.jaxrs.HalMediaType;
import com.srnjak.hateoas.relation.IanaLinkRelation;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/test")
public class TestResource {

    // tag::single[]
    @Path("/single")
    @GET
    @Produces({ // <1>
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_XML,
            HalMediaType.APPLICATION_HAL_JSON,
            HalMediaType.APPLICATION_HAL_XML})
    public Response get() {

        TestEntity testEntity = new TestEntity();
        testEntity.setId(UUID.randomUUID().toString());
        testEntity.setName("random");
        testEntity.setAge(15);
        testEntity.setEnabled(true);

        EntityModel<TestEntity> entityModel =
                new EntityModel<>(testEntity); // <2>
        entityModel.addLink(Link.builder() // <3>
                .relation(IanaLinkRelation.SELF)
                .href("http://www.example.com/")
                .build());
        entityModel.addLink(Link.builder()
                .relation(IanaLinkRelation.PREV)
                .href("http://www.example.com/prev")
                .build());
        entityModel.addLink(Link.builder()
                .relation(IanaLinkRelation.NEXT)
                .href("http://www.example.com/next")
                .build());

        return Response.ok(entityModel).build(); // <4>
    }
    // end::single[]

    // tag::single-generic[]
    @Path("/generic")
    @GET
    @Produces({ // <1>
              MediaType.APPLICATION_JSON,
              MediaType.APPLICATION_XML,
              HalMediaType.APPLICATION_HAL_JSON,
              HalMediaType.APPLICATION_HAL_XML})
    public Response generic() {

        TestEntity testEntity = new TestEntity();
        testEntity.setId(UUID.randomUUID().toString());
        testEntity.setName("random");
        testEntity.setAge(15);
        testEntity.setEnabled(true);

        TestGenericEntity<TestEntity> testGenEntity =
                new TestGenericEntity<>();
        testGenEntity.setId(UUID.randomUUID().toString());
        testGenEntity.setGenericProperty(testEntity);

        EntityModel<TestGenericEntity<TestEntity>> entityModel =
                new GenericJaxrsEntityModel<>(
                        testGenEntity, new GenericType<>(){}); // <2>

        entityModel.addLink(Link.builder() // <3>
                .relation(IanaLinkRelation.SELF)
                .href("http://www.example.com/")
                .build());
        entityModel.addLink(Link.builder()
                .relation(IanaLinkRelation.PREV)
                .href("http://www.example.com/prev")
                .build());
        entityModel.addLink(Link.builder()
                .relation(IanaLinkRelation.NEXT)
                .href("http://www.example.com/next")
                .build());

        return Response.ok(entityModel).build(); // <4>
    }
    // end::single-generic[]

    // tag::collection[]
    @Path("/collection")
    @GET
    @Produces({ // <1>
              MediaType.APPLICATION_JSON,
              MediaType.APPLICATION_XML,
              HalMediaType.APPLICATION_HAL_JSON,
              HalMediaType.APPLICATION_HAL_XML})
    public Response getAll() {

        TestEntity e1 = new TestEntity();
        e1.setId(UUID.randomUUID().toString());
        e1.setName("random1");
        e1.setAge(15);
        e1.setEnabled(true);

        TestEntity e2 = new TestEntity();
        e2.setId(UUID.randomUUID().toString());
        e2.setName("random2");
        e2.setAge(34);
        e2.setEnabled(false);

        TestEntity e3 = new TestEntity();
        e3.setId(UUID.randomUUID().toString());
        e3.setName("random3");
        e3.setAge(52);

        List<TestEntity> entityList = new ArrayList<>();
        entityList.add(e1);
        entityList.add(e2);
        entityList.add(e3);

        List<EntityModel<TestEntity>> emList = entityList.stream() // <2>
                .map(e -> {
                    EntityModel<TestEntity> entityModel = new EntityModel<>(e);
                    entityModel.addLink(Link.builder()
                            .relation(IanaLinkRelation.SELF)
                            .href("http://www.example.com/" + e.getId())
                            .build());
                    entityModel.addLink(Link.builder()
                            .relation(IanaLinkRelation.PREV)
                            .href("http://www.example.com/prev/" + e.getId())
                            .build());
                    entityModel.addLink(Link.builder()
                            .relation(IanaLinkRelation.NEXT)
                            .href("http://www.example.com/next/" + e.getId())
                            .build());
                    entityModel.addLink(Link.builder()
                            .relation(TestCurieRelation.SAMPLE)
                            .href("http://www.example.com/next/" + e.getId())
                            .build());

                    return entityModel;
                })
                .collect(Collectors.toUnmodifiableList());

        CollectionModel<TestEntity> collectionModel =
                new GenericJaxrsCollectionModel<>(
                        emList, new GenericType<>(){}); // <3>

        collectionModel.addLink( // <4>
                Link.builder()
                        .relation(IanaLinkRelation.SELF)
                        .href("http://www.example.com/")
                        .build());
        collectionModel.addLink(
                Link.builder()
                        .relation(TestCurieRelation.SAMPLE)
                        .href("http://www.example.com/")
                        .build());

        return Response.ok(collectionModel).build(); // <5>
    }
    // end::collection[]
}
