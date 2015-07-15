package com.mushroomrobot.redex;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.mushroomrobot.redex.utils.Oauth;

/**
 * Created by NLam.
 */
public class OauthActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oauth_activity);

        webView = (WebView) findViewById(R.id.webView);

        webView.loadUrl(Oauth.OAUTH_URL);
    }
}
