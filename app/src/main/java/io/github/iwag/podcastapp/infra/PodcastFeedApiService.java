package io.github.iwag.podcastapp.infra;

public class PodcastFeedApiService {

    public static PodcastFeedAPIClient create(String baseUrl) {
        return RetrofitClient.create(baseUrl).create(PodcastFeedAPIClient.class);
    }

}
