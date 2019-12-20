package com.srnjak.hateoas.relation;

import com.srnjak.hateoas.LinkRelation;
import lombok.Getter;

/**
 * Standard IANA-based link relations.
 *
 * @see <a href="https://www.iana.org/assignments/link-relations/link-relations.xhtml">
 *     https://www.iana.org/assignments/link-relations/link-relations.xhtml
 *     </a>
 */
@Getter
public class IanaLinkRelation implements LinkRelation {

    /**
     * Refers to a resource that is the subject of the link's context.
     */
    public static IanaLinkRelation	ABOUT = new IanaLinkRelation("about");

    /**
     * Refers to a substitute for this context
     */
    public static IanaLinkRelation	ALTERNATE = new IanaLinkRelation("alternate");

    /**
     * Refers to an appendix.
     */
    public static IanaLinkRelation	APPENDIX = new IanaLinkRelation("appendix");

    /**
     * Refers to an icon for the context. Synonym for icon.
     */
    public static IanaLinkRelation	APPLE_TOUCH_ICON =
            new IanaLinkRelation("appendix");
    /**
     * Refers to a launch screen for the context.
     */
    public static IanaLinkRelation	APPLE_TOUCH_STARTUP_IMAGE =
            new IanaLinkRelation("appendix");

    /**
     * Refers to a collection of records, documents, or other materials of
     * historical interest.
     */
    public static IanaLinkRelation	ARCHIVES = new IanaLinkRelation("archives");

    /**
     * Refers to the context's author.
     */
    public static IanaLinkRelation	AUTHOR = new IanaLinkRelation("author");

    /**
     * Identifies the entity that blocks access to a resource following
     * receipt of a legal demand.
     */
    public static IanaLinkRelation	BLOCKED_BY = new IanaLinkRelation("blocked-by");

    /**
     * Gives a permanent link to use for bookmarking purposes.
     */
    public static IanaLinkRelation	BOOKMARK = new IanaLinkRelation("bookmark");

    /**
     * Designates the preferred version of a resource
     * (the IRI and its contents).
     */
    public static IanaLinkRelation	CANONICAL = new IanaLinkRelation("canonical");

    /**
     * Refers to a chapter in a collection of resources.
     */
    public static IanaLinkRelation	CHAPTER = new IanaLinkRelation("chapter");

    /**
     * Indicates that the link target is preferred over the link context for
     * the purpose of referencing.
     */
    public static IanaLinkRelation	CITE_AS = new IanaLinkRelation("cite-as");

    /**
     * The target IRI points to a resource which represents the collection
     * resource for the context IRI.
     */
    public static IanaLinkRelation	COLLECTION = new IanaLinkRelation("collection");

    /**
     * Refers to a table of contents.
     */
    public static IanaLinkRelation	CONTENTS = new IanaLinkRelation("contents");

    /**
     * The document linked to was later converted to the document that contains
     * this link relation.
     */
    public static IanaLinkRelation	CONVERTED_FROM =
            new IanaLinkRelation("convertedFrom");

    /**
     * Refers to a copyright statement that applies to the link's context.
     */
    public static IanaLinkRelation	COPYRIGHT = new IanaLinkRelation("copyright");

    /**
     * The target IRI points to a resource where a submission form can be
     * obtained.
     */
    public static IanaLinkRelation	CREATE_FORM =
            new IanaLinkRelation("create-form");

    /**
     * Refers to a resource containing the most recent item(s) in a collection
     * of resources.
     */
    public static IanaLinkRelation	CURRENT = new IanaLinkRelation("current");

    /**
     * Refers to a resource providing information about the link's context.
     */
    public static IanaLinkRelation	DESCRIBED_BY =
            new IanaLinkRelation("describedby");

    /**
     * The relationship A 'describes' B asserts that resource A provides a
     * description of resource B.
     */
    public static IanaLinkRelation	DESCRIBES = new IanaLinkRelation("describes");

    /**
     * Refers to a list of patent disclosures made with respect to material for
     * which 'disclosure' relation is specified.
     */
    public static IanaLinkRelation	DISCLOSURE = new IanaLinkRelation("disclosure");

    /**
     * Used to indicate an origin that will be used to fetch required resources
     * for the link context, and that the user agent ought to resolve as early
     * as possible.
     */
    public static IanaLinkRelation	DNS_PREFETCH =
            new IanaLinkRelation("dns-prefetch");

    /**
     * Refers to a resource whose available representations are byte-for-byte
     * identical with the corresponding representations of the context IRI.
     */
    public static IanaLinkRelation	DUPLICATE = new IanaLinkRelation("duplicate");

    /**
     * Refers to a resource that can be used to edit the link's context.
     */
    public static IanaLinkRelation	EDIT = new IanaLinkRelation("edit");

    /**
     * The target IRI points to a resource where a submission form for editing
     * associated resource can be obtained.
     */
    public static IanaLinkRelation	EDIT_FORM = new IanaLinkRelation("edit-form");

    /**
     * Refers to a resource that can be used to edit media associated with the
     * link's context.
     */
    public static IanaLinkRelation	EDIT_MEDIA = new IanaLinkRelation("edit-media");

    /**
     * Identifies a related resource that is potentially large and might
     * require special handling.
     */
    public static IanaLinkRelation	ENCLOSURE = new IanaLinkRelation("enclosure");

    /**
     * Refers to a resource that is not part of the same site as the current
     * context.
     */
    public static IanaLinkRelation EXTERNAL = new IanaLinkRelation("external");

    /**
     * An IRI that refers to the furthest preceding resource in a series of
     * resources.
     */
    public static IanaLinkRelation	FIRST = new IanaLinkRelation("first");

    /**
     * Refers to a glossary of terms.
     */
    public static IanaLinkRelation	GLOSSARY = new IanaLinkRelation("glossary");

    /**
     * Refers to context-sensitive help.
     */
    public static IanaLinkRelation	HELP = new IanaLinkRelation("help");

    /**
     * Refers to a resource hosted by the server indicated by the link context.
     */
    public static IanaLinkRelation	HOSTS = new IanaLinkRelation("hosts");

    /**
     * Refers to a hub that enables registration for notification of updates to
     * the context.
     */
    public static IanaLinkRelation	HUB = new IanaLinkRelation("hub");

    /**
     * Refers to an icon representing the link's context.
     */
    public static IanaLinkRelation	ICON = new IanaLinkRelation("icon");

    /**
     * Refers to an index.
     */
    public static IanaLinkRelation	INDEX = new IanaLinkRelation("index");

    /**
     * Refers to a resource associated with a time interval that ends before
     * the beginning of the time interval associated with the context resource
     */
    public static IanaLinkRelation	INTERVAL_AFTER =
            new IanaLinkRelation("intervalAfter");

    /**
     * Refers to a resource associated with a time interval that begins after
     * the end of the time interval associated with the context resource
     */
    public static IanaLinkRelation	INTERVAL_BEFORE =
            new IanaLinkRelation("intervalBefore");

    /**
     * Refers to a resource associated with a time interval that begins after
     * the beginning of the time interval associated with the context resource,
     * and ends before the end of the time interval associated with the context
     * resource
     */
    public static IanaLinkRelation	INTERVAL_CONTAINS =
            new IanaLinkRelation("intervalContains");

    /**
     * Refers to a resource associated with a time interval that begins after
     * the end of the time interval associated with the context resource, or
     * ends before the beginning of the time interval associated with the
     * context resource
     */
    public static IanaLinkRelation	INTERVAL_DISJOINT =
            new IanaLinkRelation("intervalDisjoint");

    /**
     * Refers to a resource associated with a time interval that begins before
     * the beginning of the time interval associated with the context resource,
     * and ends after the end of the time interval associated with the context
     * resource
     */
    public static IanaLinkRelation	INTERVAL_DURING =
            new IanaLinkRelation("intervalDuring");

    /**
     * Refers to a resource associated with a time interval whose beginning
     * coincides with the beginning of the time interval associated with the
     * context resource, and whose end coincides with the end of the time
     * interval associated with the context resource
     */
    public static IanaLinkRelation	INTERVAL_EQUALS =
            new IanaLinkRelation("intervalEquals");

    /**
     * Refers to a resource associated with a time interval that begins after
     * the beginning of the time interval associated with the context resource,
     * and whose end coincides with the end of the time interval associated
     * with the context resource
     */
    public static IanaLinkRelation	INTERVAL_FINISHED_BY =
            new IanaLinkRelation("intervalFinishedBy");

    /**
     * Refers to a resource associated with a time interval that begins before
     * the beginning of the time interval associated with the context resource,
     * and whose end coincides with the end of the time interval associated
     * with the context resource
     */
    public static IanaLinkRelation	INTERVAL_FINISHES =
            new IanaLinkRelation("intervalFinishes");

    /**
     * Refers to a resource associated with a time interval that begins before
     * or is coincident with the beginning of the time interval associated with
     * the context resource, and ends after or is coincident with the end of
     * the time interval associated with the context resource
     */
    public static IanaLinkRelation	INTERVAL_IN =
            new IanaLinkRelation("intervalIn");

    /**
     * Refers to a resource associated with a time interval whose beginning
     * coincides with the end of the time interval associated with the context
     * resource
     */
    public static IanaLinkRelation	INTERVAL_MEETS =
            new IanaLinkRelation("intervalMeets");

    /**
     * Refers to a resource associated with a time interval whose beginning
     * coincides with the end of the time interval associated with the context
     * resource
     */
    public static IanaLinkRelation	INTERVAL_MET_BY =
            new IanaLinkRelation("intervalMetBy");

    /**
     * Refers to a resource associated with a time interval that begins before
     * the beginning of the time interval associated with the context resource,
     * and ends after the beginning of the time interval associated with the
     * context resource
     */
    public static IanaLinkRelation	INTERVAL_OVERLAPPED_BY =
            new IanaLinkRelation("intervalOverlappedBy");

    /**
     * Refers to a resource associated with a time interval that begins before
     * the end of the time interval associated with the context resource, and
     * ends after the end of the time interval associated with the context
     * resource
     */
    public static IanaLinkRelation	INTERVAL_OVERLAPS =
            new IanaLinkRelation("intervalOverlaps");

    /**
     * Refers to a resource associated with a time interval whose beginning
     * coincides with the beginning of the time interval associated with the
     * context resource, and ends before the end of the time interval
     * associated with the context resource
     */
    public static IanaLinkRelation	INTERVAL_STARTED_BY =
            new IanaLinkRelation("intervalStartedBy");

    /**
     * Refers to a resource associated with a time interval whose beginning
     * coincides with the beginning of the time interval associated with the
     * context resource, and ends after the end of the time interval associated
     * with the context resource
     */
    public static IanaLinkRelation	INTERVAL_STARTS =
            new IanaLinkRelation("intervalStarts");

    /**
     * The target IRI points to a resource that is a member of the collection
     * represented by the context IRI.
     */
    public static IanaLinkRelation	ITEM = new IanaLinkRelation("item");

    /**
     * An IRI that refers to the furthest following resource in a series of
     * resources.
     */
    public static IanaLinkRelation	LAST = new IanaLinkRelation("last");

    /**
     * Points to a resource containing the latest (e.g., current) version of
     * the context.
     */
    public static IanaLinkRelation	LATEST_VERSION =
            new IanaLinkRelation("latest-version");

    /**
     * Refers to a license associated with this context.
     */
    public static IanaLinkRelation	LICENSE = new IanaLinkRelation("license");

    /**
     * Refers to further information about the link's context, expressed as a
     * LRDD ("Link-based Resource Descriptor Document") resource.
     */
    public static IanaLinkRelation	LRDD = new IanaLinkRelation("lrdd");

    /**
     * Links to a manifest file for the context.
     */
    public static IanaLinkRelation	MANIFEST = new IanaLinkRelation("manifest");

    /**
     * Refers to a mask that can be applied to the icon for the context.
     */
    public static IanaLinkRelation	MASK_ICON = new IanaLinkRelation("mask-icon");

    /**
     * The Target IRI points to a Memento, a fixed resource that will not
     * change state anymore.
     */
    public static IanaLinkRelation	MEMENTO = new IanaLinkRelation("memento");

    /**
     * Links to the context's Micropub endpoint.
     */
    public static IanaLinkRelation	MICROPUB = new IanaLinkRelation("micropub");

    /**
     * Refers to a module that the user agent is to preemptively fetch and
     * store for use in the current context.
     */
    public static IanaLinkRelation	MODULEPRELOAD =
            new IanaLinkRelation("modulepreload");

    /**
     * Refers to a resource that can be used to monitor changes in an HTTP
     * resource.
     */
    public static IanaLinkRelation	MONITOR = new IanaLinkRelation("monitor");

    /**
     * Refers to a resource that can be used to monitor changes in a specified
     * group of HTTP resources.
     */
    public static IanaLinkRelation	MONITOR_GROUP =
            new IanaLinkRelation("monitor-group");

    /**
     * Indicates that the link's context is a part of a series, and that the
     * next in the series is the link target.
     */
    public static IanaLinkRelation	NEXT = new IanaLinkRelation("next");

    /**
     * Refers to the immediately following archive resource.
     */
    public static IanaLinkRelation	NEXT_ARCHIVE =
            new IanaLinkRelation("next-archive");

    /**
     * Indicates that the context‚Äôs original author or publisher does not
     * endorse the link target.
     */
    public static IanaLinkRelation	NOFOLLOW = new IanaLinkRelation("nofollow");

    /**
     * Indicates that any newly created top-level browsing context which
     * results from following the link will not be an auxiliary browsing
     * context.
     */
    public static IanaLinkRelation	NOOPENER = new IanaLinkRelation("noopener");

    /**
     * Indicates that no referrer information is to be leaked when following
     * the link.
     */
    public static IanaLinkRelation	NOREFERRER = new IanaLinkRelation("noreferrer");

    /**
     * Indicates that any newly created top-level browsing context which
     * results from following the link will be an auxiliary browsing context.
     */
    public static IanaLinkRelation	OPENER = new IanaLinkRelation("opener");

    /**
     * Refers to an OpenID Authentication server on which the context relies
     * for an assertion that the end user controls an Identifier.
     */
    public static IanaLinkRelation	OPENID2_LOCAL_ID =
            new IanaLinkRelation("openid2.local_id");

    /**
     * Refers to a resource which accepts OpenID Authentication protocol
     * messages for the context.
     */
    public static IanaLinkRelation	OPENID2_PROVIDER =
            new IanaLinkRelation("openid2.provider");

    /**
     * The Target IRI points to an Original Resource.
     */
    public static IanaLinkRelation	ORIGINAL = new IanaLinkRelation("original");

    /**
     * Refers to a P3P privacy policy for the context.
     */
    public static IanaLinkRelation	P3PV1 = new IanaLinkRelation("P3Pv1");

    /**
     * Indicates a resource where payment is accepted.
     */
    public static IanaLinkRelation	PAYMENT = new IanaLinkRelation("payment");

    /**
     * Gives the address of the pingback resource for the link context.
     */
    public static IanaLinkRelation	PINGBACK = new IanaLinkRelation("pingback");

    /**
     * Used to indicate an origin that will be used to fetch required resources
     * for the link context.
     */
    public static IanaLinkRelation	PRECONNECT = new IanaLinkRelation("preconnect");

    /**
     * Points to a resource containing the predecessor version in the version
     * history.
     */
    public static IanaLinkRelation	PREDECESSOR_VERSION =
            new IanaLinkRelation("predecessor-version");

    /**
     * The prefetch link relation type is used to identify a resource that
     * might be required by the next navigation from the link context, and that
     * the user agent ought to fetch, such that the user agent can deliver a
     * faster response once the resource is requested in the future.
     */
    public static IanaLinkRelation	PREFETCH = new IanaLinkRelation("prefetch");

    /**
     * Refers to a resource that should be loaded early in the processing of
     * the link's context, without blocking rendering.
     */
    public static IanaLinkRelation	PRELOAD = new IanaLinkRelation("preload");

    /**
     * Used to identify a resource that might be required by the next
     * navigation from the link context, and that the user agent ought to fetch
     * and execute, such that the user agent can deliver a faster response once
     * the resource is requested in the future.
     */
    public static IanaLinkRelation	PRERENDER = new IanaLinkRelation("prerender");

    /**
     * Indicates that the link's context is a part of a series, and that the
     * previous in the series is the link target.
     */
    public static IanaLinkRelation	PREV = new IanaLinkRelation("prev");

    /**
     * Refers to a resource that provides a preview of the link's context.
     */
    public static IanaLinkRelation	PREVIEW = new IanaLinkRelation("preview");

    /**
     * Refers to the previous resource in an ordered series of resources.
     * Synonym for "prev".
     */
    public static IanaLinkRelation	PREVIOUS = new IanaLinkRelation("previous");

    /**
     * Refers to the immediately preceding archive resource.
     */
    public static IanaLinkRelation	PREV_ARCHIVE =
            new IanaLinkRelation("prev-archive");

    /**
     * Refers to a privacy policy associated with the link's context.
     */
    public static IanaLinkRelation	PRIVACY_POLICY =
            new IanaLinkRelation("privacy-policy");

    /**
     * Identifying that a resource representation conforms to a certain
     * profile, without affecting the non-profile semantics of the resource
     * representation.
     */
    public static IanaLinkRelation	PROFILE = new IanaLinkRelation("profile");

    /**
     * Identifies a related resource.
     */
    public static IanaLinkRelation	RELATED = new IanaLinkRelation("related");

    /**
     * Identifies the root of RESTCONF API as configured on this HTTP server.
     */
    public static IanaLinkRelation	RESTCONF = new IanaLinkRelation("restconf");

    /**
     * Identifies a resource that is a reply to the context of the link.
     */
    public static IanaLinkRelation	REPLIES = new IanaLinkRelation("replies");

    /**
     * Refers to a resource that can be used to search through the link's
     * context and related resources.
     */
    public static IanaLinkRelation	SEARCH = new IanaLinkRelation("search");

    /**
     * Refers to a section in a collection of resources.
     */
    public static IanaLinkRelation	SECTION = new IanaLinkRelation("section");

    /**
     * Conveys an identifier for the link's context.
     */
    public static IanaLinkRelation	SELF = new IanaLinkRelation("self");

    /**
     * Indicates a URI that can be used to retrieve a service document.
     */
    public static IanaLinkRelation	SERVICE = new IanaLinkRelation("service");

    /**
     * Identifies service description for the context that is primarily
     * intended for consumption by machines.
     */
    public static IanaLinkRelation	SERVICE_DESC =
            new IanaLinkRelation("service-desc");

    /**
     * 	Identifies service documentation for the context that is primarily
     * 	intended for human consumption.
     */
    public static IanaLinkRelation	SERVICE_DOC =
            new IanaLinkRelation("service-doc");

    /**
     * 	Identifies general metadata for the context that is primarily intended
     * 	for consumption by machines.
     */
    public static IanaLinkRelation	SERVICE_META =
            new IanaLinkRelation("service-meta");

    /**
     * Refers to a resource that is within a context that is sponsored
     * (such as advertising or another compensation agreement).
     */
    public static IanaLinkRelation	SPONSORED = new IanaLinkRelation("sponsored");

    /**
     * Refers to the first resource in a collection of resources.
     */
    public static IanaLinkRelation	START = new IanaLinkRelation("start");

    /**
     * Identifies a resource that represents the context's status.
     */
    public static IanaLinkRelation	STATUS = new IanaLinkRelation("status");

    /**
     * Refers to a stylesheet.
     */
    public static IanaLinkRelation	STYLESHEET = new IanaLinkRelation("stylesheet");

    /**
     * Refers to a resource serving as a subsection in a collection of
     * resources.
     */
    public static IanaLinkRelation	SUBSECTION = new IanaLinkRelation("subsection");

    /**
     * Points to a resource containing the successor version in the version
     * history.
     */
    public static IanaLinkRelation	SUCCESSOR_VERSION =
            new IanaLinkRelation("successor-version");

    /**
     * 	Identifies a resource that provides information about the context's
     * 	retirement policy.
     */
    public static IanaLinkRelation	SUNSET = new IanaLinkRelation("sunset");

    /**
     * Gives a tag (identified by the given address) that applies to the
     * current document.
     */
    public static IanaLinkRelation	TAG = new IanaLinkRelation("tag");

    /**
     * Refers to the terms of service associated with the link's context.
     */
    public static IanaLinkRelation	TERMS_OF_SERVICE =
            new IanaLinkRelation("terms-of-service");

    /**
     * The Target IRI points to a TimeGate for an Original Resource.
     */
    public static IanaLinkRelation	TIMEGATE = new IanaLinkRelation("timegate");

    /**
     * The Target IRI points to a TimeMap for an Original Resource.
     */
    public static IanaLinkRelation	TIMEMAP = new IanaLinkRelation("timemap");

    /**
     * Refers to a resource identifying the abstract semantic type of which the
     * link's context is considered to be an instance.
     */
    public static IanaLinkRelation	TYPE = new IanaLinkRelation("type");

    /**
     * Refers to a resource that is within a context that is
     * User Generated Content.
     */
    public static IanaLinkRelation	UGC = new IanaLinkRelation("ugc");

    /**
     * Refers to a parent document in a hierarchy of documents.
     */
    public static IanaLinkRelation	UP = new IanaLinkRelation("up");

    /**
     * Points to a resource containing the version history for the context.
     */
    public static IanaLinkRelation	VERSION_HISTORY =
            new IanaLinkRelation("version-history");

    /**
     * Identifies a resource that is the source of the information in the
     * link's context.
     */
    public static IanaLinkRelation	VIA = new IanaLinkRelation("via");

    /**
     * Identifies a target URI that supports the Webmention protcol.
     */
    public static IanaLinkRelation	WEBMENTION = new IanaLinkRelation("webmention");

    /**
     * Points to a working copy for this resource.
     */
    public static IanaLinkRelation	WORKING_COPY =
            new IanaLinkRelation("working-copy");

    /**
     * Points to the versioned resource from which this working copy was
     * obtained.
     */
    public static IanaLinkRelation	WORKING_COPY_OF =
            new IanaLinkRelation("working-copy-of");

    /**
     * The relation reference.
     */
    private String reference;

    /**
     * Constructor.
     *
     * @param reference The reference of the link relation
     */
    private IanaLinkRelation(String reference) {
        this.reference = reference;
    }

    /**
     * The value of link relation
     *
     * @return The value of the link relation
     */
    @Override
    public String getValue() {
        return this.reference;
    }
}
