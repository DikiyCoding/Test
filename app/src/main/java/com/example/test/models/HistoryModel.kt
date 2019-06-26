package com.example.test.models

import android.util.Log
import com.example.test.data.LocalData
import com.example.test.database.entities.Tag
import com.example.test.service.CacheService
import com.example.test.utils.CallbackManager.ModelHistoryCallback
import com.example.test.utils.Constants
import io.reactivex.rxkotlin.subscribeBy

class HistoryModel(
    private val cacheService: CacheService,
    private val localData: LocalData
) {

    lateinit var callback: ModelHistoryCallback

    private fun initKeysOnly(keys: MutableList<Tag>) {
        for (key: Tag in keys)
            localData.history[key] = ArrayList()
    }

    fun isLocalDataEmpty(): Boolean =
        localData.history.isEmpty()

    fun getHistoryFromDatabase() =
        cacheService.getTags().subscribeBy(onSuccess = {
            initKeysOnly(it)
            callback.updateList(it)
        })

    fun getHistoryFromLocalStorage() =
        callback.updateList(localData.history.keys.toList() as MutableList<Tag>)

    fun getLastItem() =
        localData.history.keys.last()

    fun deleteItem(tag: Tag) {
        localData.history.remove(tag)
        cacheService.deleteTag(tag).subscribeBy(onComplete = {
            Log.d(
                Constants.LOGS.DATABASE.tag,
                "The history item with tag = \"${tag.value}\"" +
                        "and id = \"${tag.id}\" has been deleted successfully"
            )
        })
    }
}
