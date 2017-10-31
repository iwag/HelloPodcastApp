package io.github.iwag.newsapp.event;

import io.github.iwag.newsapp.dummy.NewsContent;
import io.github.iwag.newsapp.models.FeedItem;

public class Events {

    public static class NewsFragmentRemoveMessage {
        private Integer index;

        public NewsFragmentRemoveMessage(Integer index) {
            this.index = index;
        }

        public Integer getIndex() {
            return index;
        }
    }

    public static class NewsFragmentClickMessage {
        private final FeedItem news;

        public NewsFragmentClickMessage(FeedItem news) {
            this.news = news;
        }

        public FeedItem getNews() {
            return news;
        }
    }

    public static class NewsFragmentAddMessage {
        private final NewsContent.NewsItem news;

        public NewsFragmentAddMessage(NewsContent.NewsItem news) {
            this.news = news;
        }

        public NewsContent.NewsItem getNews() {
            return news;
        }
    }
}