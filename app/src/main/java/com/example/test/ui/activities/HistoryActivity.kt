package com.example.test.ui.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.test.R
import com.example.test.database.entities.Tag
import com.example.test.ui.adapters.HistoryAdapter
import com.example.test.ui.fragments.DeleteDialogFragment
import com.example.test.utils.App
import com.example.test.utils.CallbackManager.AdapterHistoryCallback
import com.example.test.utils.Constants
import com.example.test.utils.ViewModelFactories.HistoryViewModelFactory
import com.example.test.viewmodels.HistoryViewModel
import kotlinx.android.synthetic.main.content_history.*
import kotlinx.android.synthetic.main.content_list_empty.*
import kotlinx.android.synthetic.main.content_toolbar.*
import kotlinx.android.synthetic.main.item_list_history.view.*
import javax.inject.Inject

class HistoryActivity : AppCompatActivity(), AdapterHistoryCallback {

    @Inject
    lateinit var viewModelFactory: HistoryViewModelFactory

    private lateinit var animation: Animation
    private lateinit var history: MutableList<Tag>
    private lateinit var adapterHistory: HistoryAdapter
    private lateinit var viewModel: HistoryViewModel
    private lateinit var searchResultIntent: Intent
    private lateinit var deleteDialog: DialogFragment

    private var deletePosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        init()
    }

    override fun onItemHistoryClick(position: Int, view: View) {
        Log.d(Constants.LOGS.EVENTS.tag, "Position in history list: $position")
        view.layout_tag.startAnimation(animation)
        searchResultIntent.putExtra("tagId", history[position].id)
        startActivity(searchResultIntent)
    }

    override fun onButtonDeleteClick(position: Int) {
        Log.d(Constants.LOGS.EVENTS.tag, "\"Delete\" button is clicked")
        deletePosition = position
        showDeleteDialog()
    }

    override fun onDialogConfirmClick() {
        Log.d(Constants.LOGS.EVENTS.tag, "Dialog's \"OK\" button is clicked")
        viewModel.deleteItem(history[deletePosition])
        history.removeAt(deletePosition)
        adapterHistory.notifyDataSetChanged()
        isListEmpty()
    }

    private fun init() {
        inject()
        initViews()
        addViewModel()
        initIntent()
        createHistoryList()
        createHistoryAdapter()
        assignHistoryAdapter()
        getHistory()
    }

    private fun inject() =
        App.appComponent.inject(this)

    private fun initViews() {
        tv_title.text = getText(R.string.history)
        deleteDialog = DeleteDialogFragment()
        animation = AnimationUtils.loadAnimation(this, R.anim.anim_scale_item)
    }

    private fun addViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HistoryViewModel::class.java)
        viewModel.getLifeData().observe(this, Observer { list -> list?.let { updateList(it) } })
    }

    private fun initIntent() {
        searchResultIntent = Intent(this, SearchResultActivity::class.java)
    }

    private fun createHistoryList() {
        history = ArrayList()
    }

    private fun createHistoryAdapter() {
        adapterHistory = HistoryAdapter(history, this)
    }

    private fun assignHistoryAdapter() {
        list_history.adapter = adapterHistory
    }

    private fun updateList(tags: MutableList<Tag>) {
        hideProgressBar()
        handleListView(tags)
    }

    private fun hideProgressBar() {
        pb_history.visibility = View.GONE
    }

    private fun handleListView(tags: MutableList<Tag>) {
        history.addAll(tags)
        adapterHistory.notifyDataSetChanged()
        isListEmpty()
    }

    private fun isListEmpty() =
        if (history.isEmpty()) layout_list_empty.visibility = View.VISIBLE
        else layout_list_empty.visibility = View.GONE

    private fun getHistory() =
        viewModel.getHistory()

    private fun showDeleteDialog() =
        deleteDialog.show(supportFragmentManager, "delete")
}
