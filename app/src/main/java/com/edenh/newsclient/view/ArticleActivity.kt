package com.edenh.newsclient.view

import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.edenh.newsclient.R
import com.edenh.newsclient.utils.INTENT_ARTICLE_URL
import kotlinx.android.synthetic.main.activity_article.*

class ArticleActivity : AppCompatActivity(){

    private var articleWebView: WebView? = null
    private var articleLoader: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        articleWebView = article_content
        articleLoader = article_loader

        initWebView()
    }

    private fun initWebView() {
        val url = intent.getStringExtra(INTENT_ARTICLE_URL)
        articleWebView?.settings?.javaScriptEnabled = true
        articleWebView?.settings?.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        articleWebView?.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                articleWebView?.visibility = View.VISIBLE
                articleLoader?.visibility = View.GONE
            }
        }
        articleWebView?.loadUrl(url)
    }
}