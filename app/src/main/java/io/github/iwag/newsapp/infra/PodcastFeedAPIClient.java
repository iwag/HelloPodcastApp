package io.github.iwag.newsapp.infra;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface PodcastFeedAPIClient {
    @GET
    Call<String> getRssBody(@Url String url);
}
