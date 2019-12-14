package com.srnjak.hateoas.mediatype.hal;

/**
 * An entry of a "links" object.
 */
public interface HalLinkEntry {
    /**
     * @return The relation of the links.
     */
    String getRel();
}
