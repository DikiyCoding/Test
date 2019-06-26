package com.example.test.utils

import android.view.View
import com.example.test.database.entities.Article
import com.example.test.database.entities.Tag

object CallbackManager {

    interface ModelSearchResultCallback {
        fun updateList(list: MutableList<Article>)
        fun showError(list: MutableList<Article>)
    }

    interface ModelHistoryCallback {
        fun updateList(list: MutableList<Tag>)
    }

    interface ModelSearchCallback {
        fun updateList(list: MutableList<Pair<String, String>>)
    }

    interface AdapterHistoryCallback {
        fun onDialogConfirmClick()
        fun onItemHistoryClick(position: Int, view: View)
        fun onButtonDeleteClick(position: Int)
    }

    interface AdapterSearchCallback : AdapterHistoryCallback {
        fun onButtonFindClick(text: String)
    }
}
