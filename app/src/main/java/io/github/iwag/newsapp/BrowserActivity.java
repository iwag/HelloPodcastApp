package io.github.iwag.newsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class BrowserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        WebView webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl("http://ciccc.ca");
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
    }
}
