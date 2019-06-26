package com.example.test.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.test.R
import com.example.test.database.entities.Article
import com.example.test.utils.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_search_result.view.*

class SearchResultAdapter(
    private val articles: MutableList<Article>,
    private val callback: (Int) -> Unit
) : RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).
                inflate(R.layout.item_list_search_result, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(articles[position])

    override fun getItemCount(): Int =
        articles.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ivImage: ImageView = view.iv_image
        private val tvTitle: TextView = view.tv_item_title
        private val tvDescription: TextView = view.tv_item_description

        init {
            view.setOnClickListener { callback.invoke(adapterPosition) }
        }

        private fun addImage(article: Article) =
            if (article.imageUrl == "") addPlaceholder(Constants.NO_IMAGE_PLACEHOLDER)
            else addImageUrl(article.imageUrl)

        private fun addPlaceholder(res: Int) =
            Picasso.get()
                .load(res)
                .noFade()
                .into(ivImage)

        private fun addImageUrl(url: String) =
            Picasso.get()
                .load(url)
                .noFade()
                .into(ivImage)

        fun bind(article: Article) {
            tvTitle.text = article.title
            tvDescription.text = article.description
            addImage(article)
        }
    }
}
