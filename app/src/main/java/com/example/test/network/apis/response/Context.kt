package com.example.test.network.apis.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Context {

    @SerializedName("title")
    @Expose
    var title: String? = null
}
