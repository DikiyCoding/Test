package com.example.test.network.apis.service

import com.example.test.network.apis.response.Images
import com.example.test.utils.Constants
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface GoogleSearchApi {

    @GET(Constants.GOOGLE_SEARCH)
    fun getImages(
        @QueryMap options: MutableMap<String, String>,
        @Query("q") query: String,
        @Query("num") number: Int,
        @Query("imgSize") size: String
    ): Single<Images>
}
