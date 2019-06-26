package com.example.test.models

import android.util.Log
import com.example.test.data.LocalData
import com.example.test.database.entities.Article
import com.example.test.database.entities.Tag
import com.example.test.network.other.JSoupHandler
import com.example.test.service.CacheService
import com.example.test.service.ImagesService
import com.example.test.utils.CallbackManager.ModelSearchResultCallback
import com.example.test.utils.Constants
import io.reactivex.rxkotlin.subscribeBy
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

//TODO Pagination parsing
class SearchResultModel(
    private val handler: JSoupHandler,
    private val cacheService: CacheService,
    private val imagesService: ImagesService,
    private val localData: LocalData
) {

    private lateinit var tagValue: String
    private lateinit var articles: MutableList<Article>
    lateinit var callback: ModelSearchResultCallback

    private var isNew: Boolean = false

    private fun getDocument(tag: String) =
        handler.getDocument(tag)
            .subscribeBy(
                onSuccess = {
                    Log.d(Constants.LOGS.PARSING.tag, "HTML document is received successfully")
                    parse(it)
                },
                onError = {
                    Log.e(Constants.LOGS.PARSING.tag, "Fail to get HTML document")
                    showError()
                })

    private fun parse(document: Document) {
        for (element in document.select(Constants.PARSE_ARTICLES)) {
            val article = Article(
                Constants.DB_TABLE_ID_DEFAULT,
                getTitle(element),
                getDescription(element),
                getReference(element),
                getImageUrl(element),
                Constants.DB_TABLE_ID_DEFAULT
            )
            articles.add(article)
        }
        Log.d(Constants.LOGS.PARSING.tag, "List's size: ${articles.size}")
        updateList()
    }

    private fun showError()
            = callback.showError(ArrayList())

    private fun getImageUrl(element: Element): String {
        return if (!element.children().hasClass(Constants.PARSE_NO_IMAGE)) {
            val url = element.getElementsByTag(Constants.PARSE_IMAGE).attr(Constants.PARSE_IMAGE_ATTR)
            Log.d(Constants.LOGS.PARSING.tag, "Image's URL: $url")
            url
        } else {
            Log.d(Constants.LOGS.PARSING.tag, "Element has no image}")
            Constants.IMAGE_STATUS_NONE
        }
    }

    private fun getTitle(element: Element): String {
        val title = element.getElementsByClass(Constants.PARSE_TITLES).text()
        Log.d(Constants.LOGS.PARSING.tag, "Title: $title")
        return title
    }

    private fun getReference(element: Element): String {
        val reference = element.select(Constants.PARSE_REFERENCE).attr(Constants.PARSE_REFERENCE_ATTR)
        Log.d(Constants.LOGS.PARSING.tag, "Reference: $reference")
        return reference
    }

    //TODO Substring
    private fun getDescription(element: Element): String {
        val description = element.select(Constants.PARSE_DESCRIPTION).text()
        Log.d(Constants.LOGS.PARSING.tag, "Description: $description")
        return description
    }

    private fun updateList() {
        callback.updateList(articles)
        if (isNew) handleSaving()
    }

    private fun handleSaving() =
        imagesService.getImages(tagValue).subscribeBy(onSuccess = {
            it.items?.get(Constants.IMAGE_LIST_POSITION_DEFAULT)?.link?.let { imageTagUrl -> saveArticles(imageTagUrl) }
        })

    private fun saveArticles(imageTagUrl: String) =
        cacheService.setSearchResults(
            Tag(Constants.DB_TABLE_ID_DEFAULT, tagValue, imageTagUrl),
            articles,
            localData.history
        ).subscribeBy(onComplete = {
            Log.d(
                Constants.LOGS.DATABASE.tag,
                "The search has been saved successfully"
            )
        })

    private fun addNewData(tagValue: String) {
        Log.d(Constants.LOGS.DATA.tag,
            "Parse new data with tag \"$tagValue\"")
        this.tagValue = tagValue
        getDocument(tagValue)
    }

    //TODO Better comparison using "Tag" object
    private fun getCache(tagId: Long) {
        for (tag: Tag in localData.history.keys)
            if (tag.id == tagId) {
                if (localData.history[tag]?.size != 0)
                    getCacheFromLocalData(tag)
                else
                    getCacheFromDatabase(tag, tagId)
                break
            }
    }

    private fun getCacheFromDatabase(tag: Tag, tagId: Long) =
        cacheService.getArticlesByTagId(tagId).subscribeBy(onSuccess = {
            Log.d(
                Constants.LOGS.DATA.tag,
                "Getting data about articles from database"
            )
            articles.addAll(it)
            localData.history[tag]?.addAll(it)
            updateList()
        })

    private fun getCacheFromLocalData(tag: Tag) =
        localData.history[tag]?.let {
            Log.d(
                Constants.LOGS.DATA.tag,
                "Getting data about articles from local data"
            )
            articles.addAll(it)
            updateList()
        }

    fun getArticles(tagId: Long, tagValue: String, isNew: Boolean) {
        this.isNew = isNew
        articles = ArrayList()
        if (isNew) addNewData(tagValue)
        else getCache(tagId)
    }
}
