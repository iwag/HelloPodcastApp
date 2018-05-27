package io.github.iwag.newsapp.models;

import java.io.Serializable;

public class PodcastChannel implements Serializable {
    public String id;
    public String title;
    public String url;

    public PodcastChannel(String i, String title, String url) {
        this.id = i;
        this.title = title;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
