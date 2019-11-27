package com.srnjak.hateoas.mediatype.hal;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

/**
 * A Link representation.
 */
@Value
@Builder
@FieldNameConstants
public class HalLink {

    /**
     * Its value is either a URI [RFC3986] or a URI Template [RFC6570].
     *
     * <br>If the value is a URI Template then the Link Object SHOULD have a
     * "templated" attribute whose value is true.
     *
     * @see <a href="https://tools.ietf.org/html/rfc3986">RFC3986</a>
     * @see <a href="https://tools.ietf.org/html/rfc6570">RFC6570</a>
     */
    private String href;

    /**
     * Its value is boolean and SHOULD be true when the Link Object's "href"
     * property is a URI Template.
     *
     * <br>Its value SHOULD be considered false if it is undefined or any other
     * value than true.
     */
    private Boolean templated;

    /**
     * Its value is a string used as a hint to indicate the media type
     * expected when dereferencing the target resource.
     */
    private String type;

    /**
     * Its presence indicates that the link is to be deprecated (i.e.
     * removed) at a future date.  Its value is a URL that SHOULD provide
     * further information about the deprecation.
     *
     * <br>A client SHOULD provide some notification (for example, by logging a
     * warning message) whenever it traverses over a link that has this
     * property.  The notification SHOULD include the deprecation property's
     * value so that a client manitainer can easily find information about
     * the deprecation.
     */
    private String deprecation;

    /**
     * Its value MAY be used as a secondary key for selecting Link Objects
     * which share the same relation type.
     */
    private String name;

    /**
     * Its value is a string which is a URI that hints about the profile (as
     * defined by [I-D.wilde-profile-link]) of the target resource.
     *
     * @see <a href="https://tools.ietf.org/html/draft-kelly-json-hal-08#ref-I-D.wilde-profile-link">
     *     I-D.wilde-profile-link</a>
     */
    private String profile;

    /**
     * Its value is a string and is intended for labelling the link with a
     * human-readable identifier (as defined by [RFC5988]).
     *
     * @see <a href="https://tools.ietf.org/html/rfc5988">RFC5988</a>
     */
    private String title;

    /**
     * Its value is a string and is intended for indicating the language of
     * the target resource (as defined by [RFC5988]).
     *
     * @see <a href="https://tools.ietf.org/html/rfc5988">RFC5988</a>
     */
    private String hreflang;
}
