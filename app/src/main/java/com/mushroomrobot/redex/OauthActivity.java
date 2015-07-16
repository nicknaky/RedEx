package com.mushroomrobot.redex;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.mushroomrobot.redex.utils.Oauth;

/**
 * Created by NLam.
 */
public class OauthActivity extends AppCompatActivity {

    WebView webView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oauth_activity);

        webView = (WebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.oauth_progress);


        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if (newProgress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                }
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(ProgressBar.GONE);
                }
                //super.onProgressChanged(view, newProgress);
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);

        CookieManager.getInstance().setAcceptCookie(true);

        webView.loadUrl(Oauth.OAUTH_URL);
        Log.v("OAUTH URL", Oauth.OAUTH_URL);
    }
}
