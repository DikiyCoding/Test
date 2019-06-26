package com.example.test.database.dao

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.test.database.entities.Article
import com.example.test.database.entities.Tag

@Database(entities = [Tag::class, Article::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract val tagDAO: TagDao
    abstract val articleDAO: ArticleDao
    abstract val transactionDao: TransactionDao
}
