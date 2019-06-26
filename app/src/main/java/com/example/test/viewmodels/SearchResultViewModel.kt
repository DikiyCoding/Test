package com.example.test.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.test.database.entities.Article
import com.example.test.models.SearchResultModel
import com.example.test.utils.CallbackManager.ModelSearchResultCallback

class SearchResultViewModel(private val model: SearchResultModel)
    : ViewModel(), ModelSearchResultCallback {

    private val data: MutableLiveData<MutableList<Article>> = MutableLiveData()

    init {
        model.callback = this
    }

    override fun updateList(list: MutableList<Article>) =
        setLifeData(list)

    override fun showError(list: MutableList<Article>) =
        setLifeData(list)

    private fun setLifeData(list: MutableList<Article>) {
        data.value = list
    }

    fun getLifeData(): LiveData<MutableList<Article>> = data

    fun getSearchResult(tagId: Long, tagValue: String, isNew: Boolean) =
        model.getArticles(tagId, tagValue, isNew)
}
