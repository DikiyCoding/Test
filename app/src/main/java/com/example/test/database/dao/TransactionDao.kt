package com.example.test.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Transaction
import com.example.test.database.entities.Article
import com.example.test.database.entities.Tag

@Dao
abstract class TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertTag(tag: Tag): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertArticle(articles: MutableList<Article>): MutableList<Long>

    @Transaction
    open fun insertSearchResult(
        tag: Tag,
        articles: MutableList<Article>,
        historyList: MutableMap<Tag, MutableList<Article>>
    ) {
        setIds(tag, articles)
        historyList[tag] = articles
    }

    private fun setIds(tag: Tag, articles: MutableList<Article>) {
        tag.id = insertTag(tag)
        for(article: Article in articles)
            article.tagId = tag.id
        val articleIds = insertArticle(articles)
        for (i in 0 until articleIds.size)
            articles[i].id = articleIds[i]
    }
}
