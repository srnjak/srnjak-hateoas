package com.srnjak.hateoas.mediatype.hal;

import com.srnjak.hateoas.LinkRelation;

/**
 * An entry of an embedded object.
 */
public interface HalEmbeddedEntry {

    /**
     * @return The relation of the embedded.
     */
    LinkRelation getRel();
}
