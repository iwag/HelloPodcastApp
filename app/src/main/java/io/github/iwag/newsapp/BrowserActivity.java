package io.github.iwag.newsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class BrowserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        Intent intent = getIntent();

        WebView webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl(intent.getStringExtra("url"));
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
    }
}
