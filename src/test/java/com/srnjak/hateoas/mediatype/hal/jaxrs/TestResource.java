package com.srnjak.hateoas.mediatype.hal.jaxrs;

import com.srnjak.hateoas.CollectionModel;
import com.srnjak.hateoas.EntityModel;
import com.srnjak.hateoas.IanaLinkRelation;
import com.srnjak.hateoas.Link;
import com.srnjak.hateoas.jaxrs.model.GenericJaxrsCollectionModel;
import com.srnjak.hateoas.jaxrs.model.GenericJaxrsEntityModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/test")
public class TestResource {

    @Path("/single")
    @GET
    @Produces({
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

        EntityModel<TestEntity> entityModel = new EntityModel<>(testEntity);
        entityModel.addLink(Link.builder().relation(IanaLinkRelation.SELF).href("http://www.example.com/").build());
        entityModel.addLink(Link.builder().relation(IanaLinkRelation.PREV).href("http://www.example.com/prev").build());
        entityModel.addLink(Link.builder().relation(IanaLinkRelation.NEXT).href("http://www.example.com/next").build());

        return Response.ok(entityModel).build();
    }

    @Path("/generic")
    @GET
    @Produces({
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

        TestGenericEntity<TestEntity> testGenEntity = new TestGenericEntity<>();
        testGenEntity.setId(UUID.randomUUID().toString());
        testGenEntity.setGenericProperty(testEntity);

//        EntityModel<TestGenericEntity> entityModel = new EntityModel<>(testGenEntity);
        EntityModel<TestGenericEntity<TestEntity>> entityModel =
                new GenericJaxrsEntityModel<>(
                        testGenEntity, new GenericType<TestGenericEntity<TestEntity>>(){});

        entityModel.addLink(Link.builder().relation(IanaLinkRelation.SELF).href("http://www.example.com/").build());
        entityModel.addLink(Link.builder().relation(IanaLinkRelation.PREV).href("http://www.example.com/prev").build());
        entityModel.addLink(Link.builder().relation(IanaLinkRelation.NEXT).href("http://www.example.com/next").build());

        return Response.ok(entityModel).build();
    }

    @Path("/generic2")
    @GET
    @Produces({
                      MediaType.APPLICATION_JSON,
                      MediaType.APPLICATION_XML,
                      HalMediaType.APPLICATION_HAL_JSON,
                      HalMediaType.APPLICATION_HAL_XML})
    public Response generic2() {

        TestEntity testEntity = new TestEntity();
        testEntity.setId(UUID.randomUUID().toString());
        testEntity.setName("random");
        testEntity.setAge(15);
        testEntity.setEnabled(true);

        TestGenericEntity<TestEntity> testGenEntity = new TestGenericEntity<>();
        testGenEntity.setId(UUID.randomUUID().toString());
        testGenEntity.setGenericProperty(testEntity);

        GenericEntity<TestGenericEntity<TestEntity>> ge = new GenericEntity<>(testGenEntity) {};

        return Response.ok(ge).build();
    }

    @GET
    @Produces({
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

        List<EntityModel<TestEntity>> emList = entityList.stream()
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

                    return entityModel;
                })
                .collect(Collectors.toUnmodifiableList());

        CollectionModel<TestEntity> collectionModel =
                new GenericJaxrsCollectionModel<>(
                        emList, new GenericType<>(){});

        collectionModel.addLink(
                Link.builder()
                        .relation(IanaLinkRelation.SELF)
                        .href("http://www.example.com/")
                        .build());

        return Response.ok(collectionModel).build();
    }
}
