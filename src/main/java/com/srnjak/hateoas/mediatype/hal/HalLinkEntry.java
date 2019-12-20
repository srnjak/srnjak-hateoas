package com.srnjak.hateoas.mediatype.hal;

import com.srnjak.hateoas.LinkRelation;

/**
 * An entry of a "links" object.
 */
public interface HalLinkEntry {
    /**
     * @return The relation of the links.
     */
    LinkRelation getRel();
}
