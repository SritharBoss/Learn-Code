package com.example.cscaccount

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.MotionEvent
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


class MainActivity : AppCompatActivity() {

    lateinit var swipeRefresh:SwipeRefreshLayout;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        swipeRefresh = findViewById(R.id.swiperefresh)

        val myWebView: WebView = findViewById(R.id.webView)
        myWebView.settings.javaScriptEnabled = true
        myWebView.settings.domStorageEnabled=true
        myWebView.settings.cacheMode=WebSettings.LOAD_NO_CACHE
        myWebView.webViewClient=MyWebClient(this)
        myWebView.webChromeClient=MyChromeView(this)
        myWebView.loadUrl("https://sritharboss.github.io/csc/")

        swipeRefresh.setOnRefreshListener { myWebView.reload() }

    }
}