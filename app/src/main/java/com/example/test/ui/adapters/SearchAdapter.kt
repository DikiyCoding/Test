package com.example.test.ui.adapters

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.daimajia.swipe.SwipeLayout
import com.example.test.R
import com.example.test.database.entities.Tag
import com.example.test.utils.CallbackManager.AdapterSearchCallback
import com.example.test.utils.Constants
import com.example.test.utils.FirstItemLeftMarginDecoration
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_history.view.*
import kotlinx.android.synthetic.main.item_list_search_first.view.*

class SearchAdapter(
    private val adapterTags: TagAdapter,
    private val listHistory: MutableList<Tag>,
    private val callback: AdapterSearchCallback
) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemViewType(position: Int): Int =
        when (position) {
            0 -> 0
            else -> 1
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            0 -> ViewHolderItemFirst(inflater.inflate(R.layout.item_list_search_first, parent, false))
            else -> ViewHolderItemMain(inflater.inflate(R.layout.item_list_history, parent, false))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (this.getItemViewType(position) == 1)
            (holder as ViewHolderItemMain).bind(listHistory[position - 1])
    }

    override fun getItemCount(): Int =
        listHistory.size + 1

    /**
     * Holders:
     * 1) ViewHolderFirst
     * 2) ViewHolderMain
     */

    inner class ViewHolderItemFirst(view: View) : RecyclerView.ViewHolder(view) {

        private val etSearch: EditText = view.et_search
        private val btnSearch: Button = view.btn_search
        private val listTag: RecyclerView = view.list_tags

        init {
            listTag.adapter = adapterTags
            listTag.addItemDecoration(
                FirstItemLeftMarginDecoration(Constants.MARGINS.MARGIN15.value)
            )
            btnSearch.setOnClickListener {
                callback.onButtonFindClick(etSearch.text.toString())
            }
        }
    }

    inner class ViewHolderItemMain(view: View) : RecyclerView.ViewHolder(view) {
        private val tvValue: TextView = view.tv_value
        private val ivImage: ImageView = view.iv_image
        private val layoutSwipe: SwipeLayout = view.layout_swipe

        init {
            view.layout_click.setOnClickListener {
                callback.onItemHistoryClick(adapterPosition, view)
            }
            layoutSwipe.showMode = SwipeLayout.ShowMode.LayDown
            layoutSwipe.addDrag(
                SwipeLayout.DragEdge.Right,
                layoutSwipe.findViewWithTag(Constants.TAG_BOTTOM_VIEW_DEFAULT)
            )
            layoutSwipe.btn_delete.setOnClickListener {
                callback.onButtonDeleteClick(adapterPosition)
            }
        }

        private fun addImage(url: String) =
            Picasso.get()
                .load(url)
                .noFade()
                .into(ivImage)

        fun bind(tag: Tag) {
            tvValue.text = tag.value
            addImage(tag.imageUrl)
        }
    }
}
