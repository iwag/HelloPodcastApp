package io.github.iwag.newsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import io.github.iwag.newsapp.channellist.ChannelActivity;
import io.github.iwag.newsapp.mainlist.NewsActivity;
import io.github.iwag.newsapp.models.Channel;

public class MainActivity extends Activity {

    public void doStart(View view) {
        Intent intent = new Intent(this, ChannelActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
