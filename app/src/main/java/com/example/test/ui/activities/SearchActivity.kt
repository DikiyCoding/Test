package com.example.test.ui.activities

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.test.R
import com.example.test.database.entities.Tag
import com.example.test.ui.adapters.SearchAdapter
import com.example.test.ui.adapters.TagAdapter
import com.example.test.ui.fragments.DeleteDialogFragment
import com.example.test.utils.App
import com.example.test.utils.CallbackManager.AdapterSearchCallback
import com.example.test.utils.Constants
import com.example.test.utils.CustomFirstItemSpanSizeLookup
import com.example.test.utils.ViewModelFactories.HistoryViewModelFactory
import com.example.test.utils.ViewModelFactories.SearchViewModelFactory
import com.example.test.viewmodels.HistoryViewModel
import com.example.test.viewmodels.SearchViewModel
import kotlinx.android.synthetic.main.content_list_empty.*
import kotlinx.android.synthetic.main.content_search.*
import kotlinx.android.synthetic.main.content_toolbar.*
import kotlinx.android.synthetic.main.item_list_history.view.*
import javax.inject.Inject

class SearchActivity : AppCompatActivity(), AdapterSearchCallback {

    @Inject
    lateinit var searchViewModelFactory: SearchViewModelFactory

    @Inject
    lateinit var historyViewModelFactory: HistoryViewModelFactory

    private lateinit var animation: Animation
    private lateinit var tags: MutableList<Pair<String, String>>
    private lateinit var history: MutableList<Tag>
    private lateinit var adapterTags: TagAdapter
    private lateinit var searchResultIntent: Intent
    private lateinit var deleteDialog: DialogFragment
    private lateinit var adapterSearch: SearchAdapter
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var customGridLayoutManager: GridLayoutManager

    private var deletePosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        init()
    }

    override fun onItemHistoryClick(position: Int, view: View) {
        Log.d(Constants.LOGS.EVENTS.tag, "Position in article list: ${position - 1}")
        view.layout_tag.startAnimation(animation)
        searchResultIntent.putExtra("tagId", history[position - 1].id)
        startActivityForResult(searchResultIntent, Constants.HISTORY_LIST_NOT_CHANGED)
    }

    override fun onButtonDeleteClick(position: Int) {
        Log.d(Constants.LOGS.EVENTS.tag, "\"Delete\" button is clicked")
        deletePosition = position
        showDeleteDialog()
    }

    override fun onDialogConfirmClick() {
        Log.d(Constants.LOGS.EVENTS.tag, "Dialog's \"OK\" button is clicked")
        historyViewModel.deleteItem(history[deletePosition - 1])
        history.removeAt(deletePosition - 1)
        adapterSearch.notifyDataSetChanged()
        isListEmpty()
    }

    override fun onButtonFindClick(text: String) {
        Log.d(Constants.LOGS.EVENTS.tag, "\"Find\" button is clicked")
        val tagId = isUsedEarlier(text)
        if (tagId != Constants.ID_NOT_FOUND_VALUE) {
            onItemHistoryClick(tagId)
        } else {
            searchResultIntent.putExtra("text", text)
            startActivityForResult(searchResultIntent, Constants.HISTORY_LIST_CHANGED)
        }
    }

    private fun init() {
        inject()
        initViews()
        addViewModels()
        initIntent()
        createLists()
        createSearchAdapter()
        createCustomGridLayoutManager()
        assignSearchAdapter()
        getLists()
    }

    private fun inject() =
        App.appComponent.inject(this)

    private fun initViews() {
        tv_title.text = getText(R.string.search)
        deleteDialog = DeleteDialogFragment()
        animation = AnimationUtils.loadAnimation(this, R.anim.anim_scale_item)
    }

    private fun initIntent() {
        searchResultIntent = Intent(this, SearchResultActivity::class.java)
    }

    private fun addViewModels() {
        searchViewModel = ViewModelProviders.of(this, searchViewModelFactory).get(SearchViewModel::class.java)
        historyViewModel = ViewModelProviders.of(this, historyViewModelFactory).get(HistoryViewModel::class.java)
        searchViewModel.getLifeData().observe(this, Observer { list -> list?.let { updateTagsList(it) } })
        historyViewModel.getLifeData().observe(this, Observer { list -> list?.let { updateHistoryList(it) } })
    }

    private fun createLists() {
        tags = ArrayList()
        history = ArrayList()
    }

    private fun createSearchAdapter() {
        adapterTags = TagAdapter(tags) { position -> onItemTagsClick(position) }
        adapterSearch = SearchAdapter(adapterTags, history, this)
    }

    private fun createCustomGridLayoutManager() {
        customGridLayoutManager = GridLayoutManager(
            this,
            2,
            LinearLayoutManager.VERTICAL,
            false
        )
        customGridLayoutManager.spanSizeLookup =
            CustomFirstItemSpanSizeLookup(adapterSearch)
    }

    private fun assignSearchAdapter() {
        list_history.adapter = adapterSearch
        list_history.layoutManager = customGridLayoutManager
    }

    private fun updateTagsList(tags: MutableList<Pair<String, String>>) {
        this.tags.addAll(tags)
        adapterTags.notifyDataSetChanged()
    }

    private fun updateHistoryList() {
        history.add(historyViewModel.getLastItem())
        adapterSearch.notifyDataSetChanged()
        isListEmpty()
    }

    private fun updateHistoryList(history: MutableList<Tag>) {
        hideProgressBar()
        handleListView(history)
    }

    private fun hideProgressBar() {
        pb_search.visibility = View.GONE
    }

    private fun handleListView(history: MutableList<Tag>) {
        this.history.addAll(history)
        adapterSearch.notifyDataSetChanged()
        isListEmpty()
    }

    private fun isListEmpty() =
        if (history.isEmpty()) layout_list_empty.visibility = View.VISIBLE
        else layout_list_empty.visibility = View.GONE

    private fun showToast() =
        Toast.makeText(this, Constants.SEARCH_ERROR_RESULT, Toast.LENGTH_LONG).show()

    private fun onItemTagsClick(position: Int) {
        Log.d(Constants.LOGS.EVENTS.tag, "Position in tag list: $position")
        onButtonFindClick(tags[position].second)
    }

    private fun onItemHistoryClick(tagId: Long) {
        searchResultIntent.putExtra("tagId", tagId)
        startActivityForResult(searchResultIntent, Constants.HISTORY_LIST_NOT_CHANGED)
    }

    private fun getLists() {
        getTags()
        getHistory()
    }

    private fun getTags() =
        searchViewModel.getTags()

    private fun getHistory() =
        historyViewModel.getHistory()

    private fun clearExtras() {
        searchResultIntent.removeExtra("tagId")
        searchResultIntent.removeExtra("text")
    }

    private fun isUsedEarlier(text: String): Long =
        searchViewModel.isUsedEarlier(text)

    private fun showDeleteDialog() =
        deleteDialog.show(supportFragmentManager, "delete")

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK)
            if (data != null)
                if (data.getBooleanExtra("result", false)) {
                    Log.d(Constants.LOGS.EVENTS.tag, "Search was completed successfully")
                    if (requestCode == Constants.HISTORY_LIST_CHANGED) updateHistoryList()
                } else {
                    Log.e(Constants.LOGS.EVENTS.tag, "Search failure")
                    showToast()
                }
        clearExtras()
    }
}
