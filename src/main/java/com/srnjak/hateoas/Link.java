package com.srnjak.hateoas;

import lombok.Builder;
import lombok.Data;

/**
 * Hateoas link with a relation. It represents a hyperlink from the containing
 * resource to a URI.
 */
@Data
@Builder
public class Link {

    /**
     * The URI the link is pointing to. Its value is either a URI
     * or an URI Template.
     */
    private String href;

    /**
     * Wether href attribute is an URI Template.
     */
    private Boolean templated;

    /**
     * The type of the media type expected when dereferencing the target
     * resource.
     */
    private String type;

    /**
     * Its presence indicates that the link is to be deprecated at a future
     * date. Its value is a URL that should provide further information about
     * the deprecation.
     */
    private String deprecation;

    /**
     * The name of the link. It may be used as a secondary key for selecting
     * from links which share the same relation type.
     */
    private String name;

    /**
     * URI value that hints about the profile.
     *
     * @see <a href="https://tools.ietf.org/html/draft-wilde-profile-link-04">
     *     The 'profile' Link Relation Type draft-wilde-profile-link-04</a>
     */
    private String profile;

    /**
     * Intended for labelling the link with a human-readable identifier.
     */
    private String title;

    /**
     * Indicates the language of the target resource.
     */
    private String hreflang;

    /**
     * The link relation type describes how the current context is related to
     * the target resource.
     */
    private LinkRelation relation;
}
