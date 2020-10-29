package com.keen.minimalwebview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private static String mWebViewURL = "http://google.de";
    WebView mwview;
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout finalMySwipeRefreshLayout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mwview = findViewById(R.id.webview);

        //SwipeRefreshLayout
        finalMySwipeRefreshLayout1 = findViewById(R.id.swiperefresh);
        finalMySwipeRefreshLayout1.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // This method performs the actual data-refresh operation.
                // The method calls setRefreshing(false) when it's finished.
                mwview.loadUrl(mwview.getUrl());
            }
        });
        mProgressBar = findViewById(R.id.pb);
        startWebView();
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void startWebView() {
        WebSettings webSettings = mwview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        mwview.loadUrl(mWebViewURL);
        // Get the widgets reference from XML layout
        mProgressBar = findViewById(R.id.pb);
        mwview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // Visible the progressbar
                mProgressBar.setVisibility(View.VISIBLE);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                finalMySwipeRefreshLayout1.setRefreshing(false);
                mProgressBar.setVisibility(View.GONE);
            }
        });

        mwview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int newProgress){
                // Update the progress bar with page loading progress
                mProgressBar.setProgress(newProgress);
                if(newProgress == 100){
                    // Hide the progressbar
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}