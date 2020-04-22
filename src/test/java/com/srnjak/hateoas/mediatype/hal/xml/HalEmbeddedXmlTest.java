package com.srnjak.hateoas.mediatype.hal.xml;

import com.srnjak.hateoas.examples.entities.TestEntity;
import com.srnjak.hateoas.mediatype.hal.*;
import com.srnjak.hateoas.relation.IanaLinkRelation;
import com.srnjak.hateoas.test.utils.XmlUtils;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xmlunit.assertj.XmlAssert;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.ElementSelectors;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class HalEmbeddedXmlTest {

    @Test
    public void shouldCreateXmlElements() {

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

        HalEmbeddedXml tut = new HalEmbeddedXml(halEmbedded);

        Document actual = XmlUtils.newDocument();
        Element root = actual.createElement("root");
        actual.appendChild(root);

        String expected = "<root>"
                + "<resource rel=\"first\" href=\"http://x/1/\">"
                + " <age>1</age><enabled>true</enabled><name>e1</name>"
                + "</resource>"
                + "<resource rel=\"collection\" href=\"http://x/2/\">"
                + " <age>2</age><enabled>false</enabled><name>e2</name>"
                + "</resource>"
                + "<resource rel=\"collection\" href=\"http://x/3/\">"
                + " <age>3</age><enabled>true</enabled><name>e3</name>"
                + "</resource>"
                + "</root>";

        Set<Element> elementSet = tut.toXmlElements(actual);
        elementSet.forEach(root::appendChild);

        System.out.println(XmlUtils.toString(actual));

        assertEquals(3, elementSet.size());

        XmlAssert.assertThat(actual).and(expected)
                .ignoreWhitespace()
                .withNodeMatcher(new DefaultNodeMatcher(
                        ElementSelectors.byNameAndAllAttributes))
                .areSimilar();
    }

}