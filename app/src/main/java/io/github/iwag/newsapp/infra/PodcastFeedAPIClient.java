package io.github.iwag.newsapp.infra;

import io.github.iwag.newsapp.models.Rss;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface PodcastFeedAPIClient {
    @GET
    Call<Rss> getRss(@Url String url);
}
