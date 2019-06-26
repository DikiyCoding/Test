package com.example.test.ui.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.example.test.R
import com.example.test.database.entities.Article
import com.example.test.ui.adapters.SearchResultAdapter
import com.example.test.utils.App
import com.example.test.utils.Constants
import com.example.test.utils.LastItemBottomMarginDecoration
import com.example.test.utils.ViewModelFactories.SearchResultViewModelFactory
import com.example.test.viewmodels.SearchResultViewModel
import kotlinx.android.synthetic.main.content_search_result.*
import kotlinx.android.synthetic.main.content_toolbar.*
import javax.inject.Inject

class SearchResultActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: SearchResultViewModelFactory

    private lateinit var detailsIntent: Intent
    private lateinit var searchIntent: Intent
    private lateinit var adapterSearch: SearchResultAdapter
    private lateinit var articles: MutableList<Article>
    private lateinit var viewModel: SearchResultViewModel

    private var isNew = true
    private var tagId = Constants.ID_NOT_FOUND_VALUE
    private var tagValue = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        init()
    }

    private fun init() {
        inject()
        initViews()
        initIntents()
        getExtras()
        addViewModel()
        createArticlesList()
        createSearchAdapter()
        assignSearchAdapter()
        getArticles()
    }

    private fun inject() =
        App.appComponent.inject(this)

    private fun initViews() {
        tv_title.text = getText(R.string.articles)
    }

    private fun initIntents() {
        detailsIntent = Intent(this, DetailsActivity::class.java)
        searchIntent = intent
    }

    private fun getExtras() {
        tagId = searchIntent.getLongExtra("tagId", Constants.ID_NOT_FOUND_VALUE)
        if (tagId != Constants.ID_NOT_FOUND_VALUE) isNew = false
        else tagValue = searchIntent.getStringExtra("text")
    }

    private fun addViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchResultViewModel::class.java)
        viewModel.getLifeData().observe(this, Observer { list -> list?.let { updateList(it) } })
    }

    private fun createArticlesList() {
        articles = ArrayList()
    }

    private fun createSearchAdapter() {
        adapterSearch = SearchResultAdapter(articles) { position ->
            onItemClick(position)
        }
    }

    private fun assignSearchAdapter() {
        list_articles.adapter = adapterSearch
    }

    private fun updateList(articles: MutableList<Article>) {
        checkList(articles)
        this.articles.addAll(articles)
        list_articles.addItemDecoration(
            LastItemBottomMarginDecoration(
                Constants.MARGINS.MARGIN5.value, articles.size - 1
            )
        )
        hideProgressBar()
        adapterSearch.notifyDataSetChanged()
    }

    private fun checkList(articles: MutableList<Article>) {
        if (articles.size != 0) {
            searchIntent.putExtra("result", true)
            setResult(RESULT_OK, searchIntent)
        } else {
            searchIntent.putExtra("result", false)
            setResult(RESULT_OK, searchIntent)
            this.finish()
        }
    }

    private fun hideProgressBar() {
        pb_search_result.visibility = View.GONE
    }

    private fun onItemClick(position: Int) {
        Log.d(Constants.LOGS.EVENTS.tag, "Position in article list: $position")
        detailsIntent.putExtra("url", articles[position].reference)
        startActivity(detailsIntent)
    }

    private fun getArticles() =
        viewModel.getSearchResult(tagId, tagValue, isNew)
}
