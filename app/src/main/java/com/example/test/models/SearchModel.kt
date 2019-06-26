package com.example.test.models

import com.example.test.data.LocalData
import com.example.test.database.entities.Tag
import com.example.test.utils.CallbackManager.ModelSearchCallback
import com.example.test.utils.Constants

class SearchModel(
    private val localData: LocalData,
    val tags: MutableList<Pair<String, String>>
) {

    lateinit var callback: ModelSearchCallback

    fun isUsedEarlier(text: String): Long {
        for(tag : Tag in localData.history.keys)
            if(tag.value.equals(text, true))
                return tag.id
        return Constants.ID_NOT_FOUND_VALUE
    }
}
