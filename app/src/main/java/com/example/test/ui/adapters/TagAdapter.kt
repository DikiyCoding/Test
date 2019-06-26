package com.example.test.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.test.R
import kotlinx.android.synthetic.main.item_list_tag.view.*

class TagAdapter(
    private val tags: MutableList<Pair<String, String>>,
    private val callback: (Int) -> Unit
) : RecyclerView.Adapter<TagAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_tag, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(tags[position].first)

    override fun getItemCount(): Int =
        tags.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTag: TextView = view.tv_tag

        init {
            view.setOnClickListener { callback.invoke(adapterPosition) }
        }

        fun bind(tag: String) {
            tvTag.text = tag
        }
    }
}
