package com.example.cscaccount;

import android.content.Context;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebClient extends WebViewClient{

    private final Context context;
    MyWebClient(Context context) {
        this.context = context;
    }
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if(context instanceof MainActivity){
            MainActivity activity=(MainActivity) context;
            activity.swipeRefresh.setRefreshing(false);
        }
    }
}

