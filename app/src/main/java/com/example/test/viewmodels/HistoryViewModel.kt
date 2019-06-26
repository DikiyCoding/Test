package com.example.test.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.test.database.entities.Tag
import com.example.test.models.HistoryModel
import com.example.test.utils.CallbackManager.ModelHistoryCallback

class HistoryViewModel(private val model: HistoryModel) : ViewModel(), ModelHistoryCallback {

    private val data: MutableLiveData<MutableList<Tag>> = MutableLiveData()

    init {
        model.callback = this
    }

    override fun updateList(list: MutableList<Tag>) =
        setLifeData(list)

    private fun setLifeData(list: MutableList<Tag>) {
        data.value = list
    }

    fun getLifeData(): LiveData<MutableList<Tag>> = data

    fun getHistory() {
        if (model.isLocalDataEmpty()) model.getHistoryFromDatabase()
        else model.getHistoryFromLocalStorage()
    }

    fun getLastItem() =
        model.getLastItem()

    fun deleteItem(tag: Tag) =
        model.deleteItem(tag)
}
