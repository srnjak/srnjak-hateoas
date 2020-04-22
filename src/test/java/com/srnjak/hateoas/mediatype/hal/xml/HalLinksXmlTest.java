package com.srnjak.hateoas.mediatype.hal.xml;

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

import static org.junit.jupiter.api.Assertions.assertEquals;

class HalLinksXmlTest {

    @Test
    public void shouldAddLinkAttributesToElement() {

        Document doc = XmlUtils.newDocument();

        Element actual = doc.createElement("link");

        HalLink halLink = HalLink.builder()
                .href("http://www.example.com")
                .templated(true)
                .type("tp")
                .deprecation("depr")
                .name("nm")
                .profile("pr")
                .title("ttl")
                .hreflang("en")
                .build();

        HalLinksXml.setLinkAttributesToElement(actual, halLink);

        String expected = "<link "
                + "deprecation=\"depr\" "
                + "href=\"http://www.example.com\" "
                + "hreflang=\"en\" "
                + "name=\"nm\" "
                + "profile=\"pr\" "
                + "templated=\"true\" "
                + "title=\"ttl\" "
                + "type=\"tp\"/>";

        XmlAssert.assertThat(actual).and(expected)
                .ignoreWhitespace()
                .areSimilar();
    }

    @Test
    public void shouldConvertLinksToXmlElementSet() {

        HalLink halLink1 =
                HalLink.builder().href("http://www.example.com/1").build();
        HalLink halLink2 =
                HalLink.builder().href("http://www.example.com/2").build();
        HalLink halLink3 =
                HalLink.builder().href("http://www.example.com/3").build();
        HalLink halLink4 =
                HalLink.builder().href("http://www.example.com/4").build();

        HalLinkEntry entry1 =
                HalLinkObjectEntry.builder()
                        .rel(IanaLinkRelation.SELF)
                        .setLink(halLink1)
                        .build();

        HalLinkEntry entry2 =
                HalLinkObjectEntry.builder()
                        .rel(IanaLinkRelation.NEXT)
                        .setLink(halLink2)
                        .build();

        HalLinkEntry entry3 =
                HalLinkListEntry.builder()
                        .rel(IanaLinkRelation.FIRST)
                        .addLink(halLink3)
                        .addLink(halLink4)
                        .build();

        HalLinks halLinks = HalLinks.builder()
                .add(entry1)
                .add(entry2)
                .add(entry3)
                .build();

        HalLinksXml tut = new HalLinksXml(halLinks);

        Document actual = XmlUtils.newDocument();
        Element root = actual.createElement("root");
        actual.appendChild(root);

        // Should be without "self" link
        String expected = "<root>"
                + "<link href=\"http://www.example.com/2\" rel=\"next\"/>"
                + "<link href=\"http://www.example.com/3\" rel=\"first\"/>"
                + "<link href=\"http://www.example.com/4\" rel=\"first\"/>"
                + "</root>";

        Set<Element> elementSet = tut.toXmlElements(actual);
        elementSet.forEach(root::appendChild);

        System.out.println(XmlUtils.toString(actual));

        // Should be 3, because "self" will not be part of the set of links.
        assertEquals(3, elementSet.size());
        XmlAssert.assertThat(actual).and(expected)
                .ignoreWhitespace()
                .withNodeMatcher(new DefaultNodeMatcher(
                        ElementSelectors.byNameAndAllAttributes))
                .areSimilar();
    }
}