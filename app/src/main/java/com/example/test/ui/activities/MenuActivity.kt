package com.example.test.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.test.R
import kotlinx.android.synthetic.main.content_menu.*
import kotlinx.android.synthetic.main.content_toolbar.*

class MenuActivity : AppCompatActivity() {

    private lateinit var searchIntent: Intent
    private lateinit var historyIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        init()
    }

    private fun init() {
        initViews()
        initIntents()
        initListeners()
    }

    private fun initViews() {
        tv_title.text = getText(R.string.menu)
    }

    private fun initIntents() {
        searchIntent = Intent(this, SearchActivity::class.java)
        historyIntent = Intent(this, HistoryActivity::class.java)
    }

    private fun initListeners() {
        btn_search.setOnClickListener { startActivity(searchIntent) }
        btn_history.setOnClickListener { startActivity(historyIntent) }
    }
}
