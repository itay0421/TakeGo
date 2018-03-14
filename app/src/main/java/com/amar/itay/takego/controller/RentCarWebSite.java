package com.amar.itay.takego.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.amar.itay.takego.R;
import com.amar.itay.takego.controller.MyIntentService;

import java.net.URL;

/**
 * this calss shows the company website.
 */
@SuppressLint("Registered")
public class RentCarWebSite extends AppCompatActivity {

    //definition for the instance views we will get.
    WebView webView;
    Intent intent =null;
    String Url;

    /**
     * @param savedInstanceState contains the most recent data, specially contains
     * data of the activity's previous initialization part.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_car_web_site);
        intent = getIntent();
        Url = intent.getStringExtra("webUrl");
        findViews();
    }

    /**
     * find the views.
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void findViews() {


        webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //improve webView performance
//        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        webSettings.setAppCacheEnabled(true);
//        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        webSettings.setDomStorageEnabled(true);
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        webSettings.setUseWideViewPort(true);
//        webSettings.setSavePassword(true);
//        webSettings.setSaveFormData(true);
//        webSettings.setEnableSmoothTransition(true);


        //open the website Url.
        webView.loadUrl("https://www.google.co.il/search?q=picture&tbm=isch&source=iu&ictx=1&fir=8mNyFG2hn_NzJM%253A%252CDK_kK4jcw6ui8M%252C_&usg=__fX0qvhKhegLlUrmkr4j5F0pq68M%3D&sa=X&ved=0ahUKEwigh5KNvvHYAhWSKlAKHZrpA9UQ9QEILjAC#imgrc=8mNyFG2hn_NzJM:");
        //force links open in web view only
        //webView.setWebViewClient(new WebViewClient());
    }

}
