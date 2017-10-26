package io.github.iwag.newsapp.infra;

public class InterviewApiService {
    public static InterviewAPIClient interviewClient = null;

    public static final String BASE_URL = "http://calm-wave-21290.herokuapp.com/";

    public static void initialize() {
        interviewClient = RetrofitClient.getClient(BASE_URL).create(InterviewAPIClient.class);
    }

}
