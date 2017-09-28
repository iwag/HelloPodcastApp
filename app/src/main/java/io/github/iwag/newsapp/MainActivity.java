package io.github.iwag.newsapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import io.github.iwag.newsapp.dummy.DummyContent;

public class MainActivity extends Activity {

    public void doStart(View view) {
        Intent intent = new Intent(this, NewsActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
