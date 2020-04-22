package com.srnjak.hateoas.mediatype.hal;

import com.srnjak.hateoas.*;
import com.srnjak.hateoas.examples.relations.TestCurieRelation;
import com.srnjak.hateoas.relation.IanaLinkRelation;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HalMapperTest {

    @Test
    public void convert_HypermediaModel() {

        HypermediaModel hypermediaModel = new HypermediaModel(
                generateIanaLink(IanaLinkRelation.SELF),
                generateIanaLink(IanaLinkRelation.ABOUT),
                generateCurieLink());

//        System.out.println("hypermediaModel=" + hypermediaModel);

        // call conversion method
        HalObject halObject = HalMapper.toHalObject(hypermediaModel);

//        System.out.println("halObject=" + halObject);

        assertNotNull(halObject);
        assertEquals(3, halObject.getHalLinks().getEntrySet().size());
        assertNull(halObject.getEmbedded());
    }

    @Test
    public void convert_EntityModel() {

        Object entity = new Object();

        EntityModel<Object> entityModel =
                new EntityModel<>(
                        entity,
                        generateIanaLink(IanaLinkRelation.SELF),
                        generateIanaLink(IanaLinkRelation.ABOUT),
                        generateCurieLink());

//        System.out.println("entityModel=" + entityModel);

        // call conversion method
        HalObject halObject = HalMapper.toHalObject(entityModel);

//        System.out.println("halObject=" + halObject);

        assertNotNull(halObject);
        assertSame(entity, halObject.getObject());
        assertEquals(3, halObject.getHalLinks().getEntrySet().size());
        assertNull(halObject.getEmbedded());
    }

    @Test
    public void convert_CollectionModel() {
        Object entity1 = new Object();
        Object entity2 = new Object();

        EntityModel<Object> entityModel1 =
                new EntityModel<>(
                        entity1,
                        generateIanaLink(IanaLinkRelation.SELF));

        EntityModel<Object> entityModel2 =
                new EntityModel<>(
                        entity2,
                        generateIanaLink(IanaLinkRelation.SELF));

        List<EntityModel<Object>> entityList = new ArrayList<>();
        entityList.add(entityModel1);
        entityList.add(entityModel2);

        CollectionModel<Object> collectionModel =
                new CollectionModel<>(
                        entityList,
                        generateIanaLink(IanaLinkRelation.SELF),
                        generateIanaLink(IanaLinkRelation.ABOUT),
                        generateCurieLink());

//        System.out.println("collectionModel=" + collectionModel);

        // call conversion method
        HalObject halObject = HalMapper.toHalObject(collectionModel);

//        System.out.println("halObject=" + halObject);

        assertNotNull(halObject);
        assertNull(halObject.getObject());

        // verify links
        assertEquals(3, halObject.getHalLinks().getEntrySet().size());

        // verify embedded
        assertNotNull(halObject.getEmbedded());
        assertEquals(1, halObject.getEmbedded().getEntrySet().size());

        // verify embedded item
        HalEmbeddedEntry embeddedEntry =
                halObject.getEmbedded().getEntrySet().iterator().next();
        assertEquals(IanaLinkRelation.ITEM, embeddedEntry.getRel());
        assertTrue(embeddedEntry instanceof HalEmbeddedListEntry);

        // verify embedded list entries
        HalEmbeddedListEntry halEmbeddedListEntry =
                (HalEmbeddedListEntry) embeddedEntry;
        assertEquals(2, halEmbeddedListEntry.getHalObjectList().size());
        assertSame(
                entity1,
                halEmbeddedListEntry.getHalObjectList().get(0).getObject());
        assertSame(
                entity2,
                halEmbeddedListEntry.getHalObjectList().get(1).getObject());
    }

    @Test
    public void convert_Link() {
        Link link = Link.builder()
                .relation(IanaLinkRelation.SELF)
                .href("hrefstr")
                .templated(true)
                .type("type")
                .deprecation("deprecated")
                .name("name")
                .profile("profile")
                .title("title")
                .hreflang("lang")
                .build();

        HalLink halLink = HalMapper.toHalLink(link);

        assertEquals(link.getHref(), halLink.getHref());
        assertEquals(link.getTemplated(), halLink.getTemplated());
        assertEquals(link.getType(), halLink.getType());
        assertEquals(link.getDeprecation(), halLink.getDeprecation());
        assertEquals(link.getName(), halLink.getName());
        assertEquals(link.getProfile(), halLink.getProfile());
        assertEquals(link.getTitle(), halLink.getTitle());
        assertEquals(link.getHreflang(), halLink.getHreflang());
    }

    /**
     * Generates {@link Link} with a {@link IanaLinkRelation} relation type.
     *
     * @param ianaLinkRelation The {@link IanaLinkRelation} relation type
     * @return The generated link
     */
    private Link generateIanaLink(IanaLinkRelation ianaLinkRelation) {
        return Link.builder()
                .relation(ianaLinkRelation)
                .href(RandomStringUtils.randomAlphabetic(10))
                .build();
    }

    /**
     * Generates {@link Link} with a curied relation.
     *
     * @return The generated link
     */
    private Link generateCurieLink() {

        return Link.builder()
                .relation(TestCurieRelation.SAMPLE)
                .href(RandomStringUtils.randomAlphabetic(10))
                .build();
    }
}