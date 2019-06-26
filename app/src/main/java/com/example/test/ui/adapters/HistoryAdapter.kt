package com.example.test.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.daimajia.swipe.SwipeLayout
import com.example.test.R
import com.example.test.database.entities.Tag
import com.example.test.utils.CallbackManager.AdapterHistoryCallback
import com.example.test.utils.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_history.view.*

class HistoryAdapter(
    private val tags: MutableList<Tag>,
    private val callback: AdapterHistoryCallback
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_list_history, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(tags[position])

    override fun getItemCount(): Int =
        tags.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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
