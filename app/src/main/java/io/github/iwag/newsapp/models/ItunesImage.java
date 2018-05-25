package io.github.iwag.newsapp.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class ItunesImage {
    @Attribute
    private String href;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
