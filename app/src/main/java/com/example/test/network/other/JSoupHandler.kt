package com.example.test.network.other

import com.example.test.utils.Constants
import com.example.test.utils.subscribeSingleOnIoObserveOnUi
import io.reactivex.Single
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class JSoupHandler {

    fun getDocument(tag: String): Single<Document> =
        Single.defer { Single.just(Jsoup.connect(Constants.PARSE_BASE_URL + tag).get()) }
            .subscribeSingleOnIoObserveOnUi()
}
