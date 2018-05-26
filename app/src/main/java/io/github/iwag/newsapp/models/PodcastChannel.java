package io.github.iwag.newsapp.models;

import java.io.Serializable;

public class PodcastChannel implements Serializable {
    public int id;
    public String content;
    public String url;

    public PodcastChannel(int i, String content, String s) {
        this.id = i;
        this.content = content;
        this.url = s;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
