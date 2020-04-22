package com.srnjak.hateoas.mediatype.hal.xml;

import com.srnjak.hateoas.examples.relations.TestCurieRelation;
import com.srnjak.hateoas.examples.entities.TestEntity;
import com.srnjak.hateoas.mediatype.hal.*;
import com.srnjak.hateoas.relation.IanaLinkRelation;
import com.srnjak.hateoas.test.utils.XmlUtils;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xmlunit.assertj.XmlAssert;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.ElementSelectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HalObjectXmlTest {

    @Test
    public void shouldCreateXmlDocument_whenWithSelfLinkOnly() {

        TestEntity testEntity = new TestEntity();
        testEntity.setName("xxx");
        testEntity.setAge(123);
        testEntity.setEnabled(true);

        HalLink halLink = HalLink.builder()
                .href("http://www.example.com/")
                .build();

        HalObject halObject = HalObject.builder(testEntity)
                .addLink(IanaLinkRelation.SELF, halLink)
                .build();

        HalObjectXml tut = new HalObjectXml(halObject);

        String expected = "<resource rel=\"self\" "
                + "href=\"http://www.example.com/\">"
                + "<age>123</age>"
                + "<enabled>true</enabled>"
                + "<name>xxx</name>"
                + "</resource>";

        Document actual = tut.toXmlDocument();

        XmlAssert.assertThat(actual).and(expected)
                .ignoreWhitespace()
                .areSimilar();
    }

    @Test
    public void shouldCreateXmlDocument_whenWithLinks() {

        TestEntity testEntity = new TestEntity();
        testEntity.setName("xxx");
        testEntity.setAge(123);
        testEntity.setEnabled(true);

        HalLink halLink1 = HalLink.builder()
                .href("http://www.example.com/1/")
                .build();
        HalLink halLink2 = HalLink.builder()
                .href("http://www.example.com/2/")
                .build();
        HalLink halLink3 = HalLink.builder()
                .href("http://www.example.com/3/")
                .build();

        HalObject halObject = HalObject.builder(testEntity)
                .addLink(IanaLinkRelation.SELF, halLink1)
                .addLink(IanaLinkRelation.FIRST, halLink2)
                .addLink(IanaLinkRelation.LAST, halLink3)
                .build();

        HalObjectXml tut = new HalObjectXml(halObject);

        String expected = "<resource rel=\"self\" "
                + "href=\"http://www.example.com/1/\">"
                + "<link href=\"http://www.example.com/2/\" rel=\"first\"/>"
                + "<link href=\"http://www.example.com/3/\" rel=\"last\"/>"
                + "<age>123</age>"
                + "<enabled>true</enabled>"
                + "<name>xxx</name>"
                + "</resource>";

        Document actual = tut.toXmlDocument();

        System.out.println(XmlUtils.toString(actual));

        XmlAssert.assertThat(actual).and(expected)
                .ignoreWhitespace()
                .ignoreChildNodesOrder()
                .withNodeMatcher(new DefaultNodeMatcher(
                        ElementSelectors.byNameAndAllAttributes))
                .areSimilar();
    }

    @Test
    public void shouldCreateXmlDocument_whenWithCurieLinks() {

        TestEntity testEntity = new TestEntity();
        testEntity.setName("xxx");
        testEntity.setAge(123);
        testEntity.setEnabled(true);

        HalLink halLink1 = HalLink.builder()
                .href("http://www.example.com/1/")
                .build();
        HalLink halLink2 = HalLink.builder()
                .href("http://www.example.com/2/")
                .build();

        HalObject halObject = HalObject.builder(testEntity)
                .addLink(IanaLinkRelation.SELF, halLink1)
                .addLink(TestCurieRelation.SAMPLE, halLink2)
                .build();

        HalObjectXml tut = new HalObjectXml(halObject);

        String expected = "<resource xmlns:ts=\"http://www.test.com/\" "
                + "rel=\"self\" href=\"http://www.example.com/1/\">"
                + "<link href=\"http://www.example.com/2/\" rel=\"ts:sample\"/>"
                + "<age>123</age>"
                + "<enabled>true</enabled>"
                + "<name>xxx</name>"
                + "</resource>";

        Document actual = tut.toXmlDocument();

        System.out.println(XmlUtils.toString(actual));

        XmlAssert.assertThat(actual).and(expected)
                .ignoreWhitespace()
                .ignoreChildNodesOrder()
                .withNodeMatcher(new DefaultNodeMatcher(
                        ElementSelectors.byNameAndAllAttributes))
                .areSimilar();
    }

    @Test
    public void shouldCreateXmlDocument_whenWithLinksAndEmbedded() {

        // entity
        TestEntity testEntity = new TestEntity();
        testEntity.setName("xxx");
        testEntity.setAge(123);
        testEntity.setEnabled(true);

        // links
        HalLink halLink1 = HalLink.builder()
                .href("http://www.example.com/1/")
                .build();
        HalLink halLink2 = HalLink.builder()
                .href("http://www.example.com/2/")
                .build();
        HalLink halLink3 = HalLink.builder()
                .href("http://www.example.com/3/")
                .build();

        // embedded
        TestEntity e1 = new TestEntity();
        e1.setName("e1");
        e1.setAge(1);
        e1.setEnabled(true);

        TestEntity e2 = new TestEntity();
        e2.setName("e2");
        e2.setAge(2);
        e2.setEnabled(false);

        TestEntity e3 = new TestEntity();
        e3.setName("e3");
        e3.setAge(3);
        e3.setEnabled(true);

        HalObject o1 = HalObject.builder(e1)
                .addLink(IanaLinkRelation.SELF, HalLink.builder()
                        .href("http://x/1/").build())
                .build();
        HalObject o2 = HalObject.builder(e2)
                .addLink(IanaLinkRelation.SELF, HalLink.builder()
                        .href("http://x/2/").build())
                .build();
        HalObject o3 = HalObject.builder(e3)
                .addLink(IanaLinkRelation.SELF, HalLink.builder()
                        .href("http://x/3/").build())
                .build();

        HalEmbeddedEntry objectEntry =
                HalEmbeddedObjectEntry.builder(IanaLinkRelation.FIRST)
                        .object(o1)
                        .build();
        HalEmbeddedEntry listEntry =
                HalEmbeddedListEntry.builder(IanaLinkRelation.COLLECTION)
                        .addObject(o2)
                        .addObject(o3)
                        .build();

        HalEmbedded halEmbedded =
                HalEmbedded.builder().add(objectEntry).add(listEntry).build();

        HalObject halObject = HalObject.builder(testEntity)
                .addLink(IanaLinkRelation.SELF, halLink1)
                .addLink(IanaLinkRelation.FIRST, halLink2)
                .addLink(IanaLinkRelation.LAST, halLink3)
                .addEmbedded(halEmbedded)
                .build();

        HalObjectXml tut = new HalObjectXml(halObject);

        String expected =
                "<resource rel=\"self\" href=\"http://www.example.com/1/\">"
                + "<link href=\"http://www.example.com/2/\" rel=\"first\"/>"
                + "<link href=\"http://www.example.com/3/\" rel=\"last\"/>"
                + "<resource href=\"http://x/1/\" rel=\"first\">"
                + "  <age>1</age>\n"
                + "  <enabled>true</enabled>\n"
                + "  <name>e1</name>\n"
                + "</resource>"
                + "<resource href=\"http://x/2/\" rel=\"collection\">"
                + "  <age>2</age>\n"
                + "  <enabled>false</enabled>\n"
                + "  <name>e2</name>\n"
                + "</resource>"
                + "<resource href=\"http://x/3/\" rel=\"collection\">"
                + "  <age>3</age>\n"
                + "  <enabled>true</enabled>\n"
                + "  <name>e3</name>\n"
                + "</resource>"
                + "<age>123</age>"
                + "<enabled>true</enabled>"
                + "<name>xxx</name>"
                + "</resource>";

        Document actual = tut.toXmlDocument();

        System.out.println(XmlUtils.toString(actual));

        XmlAssert.assertThat(actual).and(expected)
                .ignoreWhitespace()
                .ignoreChildNodesOrder()
                .withNodeMatcher(new DefaultNodeMatcher(
                        ElementSelectors.byNameAndAllAttributes))
                .areSimilar();
    }

    @Test
    public void shouldCreateXmlDocument_fail_WhenWithoutLinksAndEmbedded() {

        TestEntity testEntity = new TestEntity();
        testEntity.setName("xxx");
        testEntity.setAge(123);
        testEntity.setEnabled(true);

        HalObject halObject = HalObject.builder(testEntity).build();
        HalObjectXml tut = new HalObjectXml(halObject);

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class, tut::toXmlDocument);
        assertEquals(HalObjectXml.MISSING_SELF_LINK, e.getMessage());
    }

    @Test
    public void shouldCreateXmlDocument_fail_WhenSelfLinkMissing() {

        // entity
        TestEntity testEntity = new TestEntity();
        testEntity.setName("xxx");
        testEntity.setAge(123);
        testEntity.setEnabled(true);

        // links
        HalLink halLink = HalLink.builder()
                .href("http://www.example.com/1/")
                .build();

        HalObject halObject = HalObject.builder(testEntity)
                .addLink(IanaLinkRelation.FIRST, halLink)
                .build();

        HalObjectXml tut = new HalObjectXml(halObject);

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class, tut::toXmlDocument);
        assertEquals(HalObjectXml.MISSING_SELF_LINK, e.getMessage());
    }

    @Test
    public void shouldCreateXmlDocument_fail_WhenEmbeddedMissingSelf() {

        // entity
        TestEntity testEntity = new TestEntity();
        testEntity.setName("xxx");
        testEntity.setAge(123);
        testEntity.setEnabled(true);

        // links
        HalLink halLink = HalLink.builder()
                .href("http://www.example.com/1/")
                .build();

        // embedded
        TestEntity e1 = new TestEntity();
        e1.setName("e1");
        e1.setAge(1);
        e1.setEnabled(true);

        HalObject o1 = HalObject.builder(e1).build();

        HalEmbeddedEntry objectEntry =
                HalEmbeddedObjectEntry.builder(IanaLinkRelation.FIRST)
                        .object(o1)
                        .build();

        HalEmbedded halEmbedded =
                HalEmbedded.builder().add(objectEntry).build();

        HalObject halObject = HalObject.builder(testEntity)
                .addLink(IanaLinkRelation.SELF, halLink)
                .addEmbedded(halEmbedded)
                .build();

        HalObjectXml tut = new HalObjectXml(halObject);

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class, tut::toXmlDocument);
        assertEquals(HalObjectXml.MISSING_SELF_LINK, e.getMessage());
    }

}