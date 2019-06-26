package com.example.test.database.dao

import android.arch.persistence.room.*
import com.example.test.database.entities.Article
import io.reactivex.Single

@Dao
interface ArticleDao {

    @Query("SELECT * FROM article")
    fun getAll(): Single<MutableList<Article>>

    @Query("SELECT * FROM article WHERE id = :id")
    fun getById(id: Long): Single<Article>

    @Query("SELECT * FROM article WHERE title = :title")
    fun getByTitle(title: String): Single<Article>

    @Query("SELECT * FROM article WHERE tag_id = :tagId")
    fun getByTagId(tagId: Long): Single<MutableList<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: Article): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(articles: MutableList<Article>): MutableList<Long>

    @Update
    fun update(article: Article)

    @Delete
    fun delete(article: Article)
}
