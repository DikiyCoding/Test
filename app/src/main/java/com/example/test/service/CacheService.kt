package com.example.test.service

import com.example.test.database.dao.ArticleDao
import com.example.test.database.dao.TagDao
import com.example.test.database.dao.TransactionDao
import com.example.test.database.entities.Article
import com.example.test.database.entities.Tag
import com.example.test.utils.subscribeCompletableOnIoObserveOnUi
import com.example.test.utils.subscribeSingleOnIoObserveOnUi
import io.reactivex.Completable
import io.reactivex.Single

class CacheService(
    private val tagDao: TagDao,
    private val articleDao: ArticleDao,
    private val transactionDao: TransactionDao
) {
    /**
     * Tags
     */

    fun getTags(): Single<MutableList<Tag>> =
        tagDao.getAll().subscribeSingleOnIoObserveOnUi()

    fun getTagById(id: Long): Single<Tag> =
        tagDao.getById(id).subscribeSingleOnIoObserveOnUi()

    fun getTagByValue(value: String): Single<Tag> =
        tagDao.getByValue(value).subscribeSingleOnIoObserveOnUi()

    fun setTag(tag: Tag): Completable =
        Completable.fromAction { tagDao.insert(tag) }
            .subscribeCompletableOnIoObserveOnUi()

    fun setTags(tags: MutableList<Tag>): Completable =
        Completable.fromAction { tagDao.insert(tags) }
            .subscribeCompletableOnIoObserveOnUi()

    fun updateTag(tag: Tag): Completable =
        Completable.fromAction { tagDao.update(tag) }
            .subscribeCompletableOnIoObserveOnUi()

    fun deleteTag(tag: Tag): Completable =
        Completable.fromAction { tagDao.delete(tag) }
            .subscribeCompletableOnIoObserveOnUi()

    /**
     * Articles
     */

    fun getArticles(): Single<MutableList<Article>> =
        articleDao.getAll().subscribeSingleOnIoObserveOnUi()

    fun getArticleById(id: Long): Single<Article> =
        articleDao.getById(id).subscribeSingleOnIoObserveOnUi()

    fun getArticleByTitle(title: String): Single<Article> =
        articleDao.getByTitle(title).subscribeSingleOnIoObserveOnUi()

    fun getArticlesByTagId(tagId: Long): Single<MutableList<Article>> =
        articleDao.getByTagId(tagId).subscribeSingleOnIoObserveOnUi()

    fun setArticle(article: Article): Completable =
        Completable.fromAction { articleDao.insert(article) }
            .subscribeCompletableOnIoObserveOnUi()

    fun setArticles(articles: MutableList<Article>): Completable =
        Completable.fromAction { articleDao.insert(articles) }
            .subscribeCompletableOnIoObserveOnUi()

    fun updateArticle(article: Article): Completable =
        Completable.fromAction { articleDao.update(article) }
            .subscribeCompletableOnIoObserveOnUi()

    fun deleteArticle(article: Article): Completable =
        Completable.fromAction { articleDao.delete(article) }
            .subscribeCompletableOnIoObserveOnUi()

    /**
     * Transactions
     */

    fun setSearchResults(
        tag: Tag,
        articles: MutableList<Article>,
        historyList: MutableMap<Tag, MutableList<Article>>
    ) = Completable.fromAction { transactionDao.insertSearchResult(tag, articles, historyList) }
        .subscribeCompletableOnIoObserveOnUi()
}
