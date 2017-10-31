package io.github.iwag.newsapp.dummy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class NewsContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<NewsItem> ITEMS = new ArrayList<NewsItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, NewsItem> ITEM_MAP = new HashMap<String, NewsItem>();

    private static final int COUNT = 25;

    private static final Random random = new Random();

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createNewsItem(i));
        }
    }

    private static void addItem(NewsItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static NewsItem createNewsItem(int position) {
        String userName = "User" + String.valueOf(position);
        Date datee = new Date(2017-1900-random.nextInt(10), 10, position);
        String iconUrl = "http://www.edamam.com/web-img/5f5/5f51c89f832d50da84e3c05bef3f66f9.jpg";
        String imageUrl1 = "https://www.edamam.com/web-img/341/3417c234dadb687c0d3a45345e86bff4.jpg";
        String imageUrl2 = "https://www.edamam.com/web-img/c7d/c7d9644d5e696ec0a99af1563d3f3fc3.jpg";
        int likes = 3;
        int comments = 2;
        return new NewsItem(userName, "Content " + position, makeDetails(position), datee, iconUrl, imageUrl1, imageUrl2, likes, comments);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about FeedItem: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class NewsItem {
        public final String id;
        public final String content;
        public final String details;
        public final Date date;
        public final String iconUrl;
        public final String imageUrl1;
        public final String imageUrl2;
        public final Integer likes;
        public final Integer comments;

        static SimpleDateFormat SMD = new SimpleDateFormat("yyyy-MM-dd hh:mm:dd");

        public NewsItem(String id, String content, String details, Date date, String iconUrl, String imageUrl1, String imageUrl2, Integer likes, Integer comments) {
            this.id = id;
            this.content = content;
            this.details = details;
            this.date = date;
            this.iconUrl = iconUrl;
            this.imageUrl1 = imageUrl1;
            this.imageUrl2 = imageUrl2;
            this.likes = likes;
            this.comments = comments;
        }

        public NewsItem(String id, String content, String details, Long longDate, String iconUrl, String imageUrl1, String imageUrl2, Integer likes, Integer comments) {
            this.id = id;
            this.content = content;
            this.details = details;
            this.date = new Date(longDate);
            this.iconUrl = iconUrl;
            this.imageUrl1 = imageUrl1;
            this.imageUrl2 = imageUrl2;
            this.likes = likes;
            this.comments = comments;
        }


        @Override
        public String toString() {
            return content;
        }
    }
}
