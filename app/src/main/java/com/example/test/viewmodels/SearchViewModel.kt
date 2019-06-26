package com.example.test.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.test.models.SearchModel
import com.example.test.utils.CallbackManager.ModelSearchCallback

class SearchViewModel(private val model: SearchModel) : ViewModel(), ModelSearchCallback {

    private val data: MutableLiveData<MutableList<Pair<String, String>>> = MutableLiveData()

    init {
        model.callback = this
    }

    override fun updateList(list: MutableList<Pair<String, String>>) {}

    private fun setLifeData(tags: MutableList<Pair<String, String>>) {
        data.value = tags
    }

    fun getLifeData(): LiveData<MutableList<Pair<String, String>>> = data

    fun getTags()= setLifeData(model.tags)

    fun isUsedEarlier(text: String): Long =
        model.isUsedEarlier(text)
}
