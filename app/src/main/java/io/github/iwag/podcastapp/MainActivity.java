package io.github.iwag.podcastapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import io.github.iwag.podcastapp.channellist.ChannelListActivity;

public class MainActivity extends Activity {

    public void doStart(View view) {
        Intent intent = new Intent(this, ChannelListActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
