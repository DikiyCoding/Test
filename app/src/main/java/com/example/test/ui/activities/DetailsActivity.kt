package com.example.test.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.test.R
import kotlinx.android.synthetic.main.content_details.*
import kotlinx.android.synthetic.main.content_toolbar.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initViews()
    }

    private fun initViews() {
        initTitleToolbar()
        initWebView()
    }

    private fun initTitleToolbar() {
        tv_title.text = getText(R.string.article)
    }

    private fun initWebView() {
        web_view_detail.settings.javaScriptEnabled = true
        web_view_detail.loadUrl(intent.getStringExtra("url"))

        web_view_detail.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                pb_details.visibility = View.GONE
            }
        }
    }
}
