package com.example.admin.newsfeed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {

    private WebView mDisplayWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        mDisplayWebView = findViewById(R.id.display_wv);
        mDisplayWebView.setWebViewClient(new WebViewClient());
        mDisplayWebView.loadUrl(url);
    }
}
