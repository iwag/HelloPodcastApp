package io.github.iwag.podcastapp.infra;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(JacksonConverterFactory.create()).build();
        }
        return retrofit;
    }

    public static Retrofit create(String baseUrl) {
        return new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(ScalarsConverterFactory.create()).build();
    }
}
