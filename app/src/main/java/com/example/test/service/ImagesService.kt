package com.example.test.service

import com.example.test.network.apis.response.Images
import com.example.test.network.apis.service.GoogleSearchApi
import com.example.test.utils.Constants
import com.example.test.utils.subscribeSingleOnIoObserveOnUi
import io.reactivex.Single

class ImagesService(
    private val google: GoogleSearchApi,
    private val params: MutableMap<String, String>
) {

    //TODO ApiKeyInterceptor
    fun getImages(query: String): Single<Images> =
        google.getImages(
            params, query,
            Constants.IMAGE_NUMBER_DEFAULT,
            Constants.IMAGE_SIZE_DEFAULT
        ).subscribeSingleOnIoObserveOnUi()
}
