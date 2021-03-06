package io.github.iwag.podcastapp.models;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import java.io.Serializable;


@Root(name = "item", strict = false)
public final class FeedItem {

    @Element(name = "title")
    public String title;

    @Namespace(reference = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    @Element(name = "subtitle", required = false)
    public String subTitle;

    @Element(name = "description", required = false)
    public String description;

    @Element(name = "pubDate", required = false)
    public String pubDate;

    @Element(name = "link", required = false)
    public String link;

    @Namespace(reference = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    @Element(name = "duration", required = false)
    public String duration;

    @Element(name = "enclosure")
    public Enclosure enclosure;

    @Root(strict = false)
    public static class Enclosure {
        @Attribute(name = "url")
        public String url;

        @Attribute(name = "type")
        public String type;

        @Attribute(name = "length")
        public String length;
    }
}