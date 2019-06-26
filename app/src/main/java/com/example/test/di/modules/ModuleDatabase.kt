package com.example.test.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.example.test.data.LocalData
import com.example.test.database.dao.AppDataBase
import com.example.test.database.dao.ArticleDao
import com.example.test.database.dao.TagDao
import com.example.test.database.dao.TransactionDao
import com.example.test.utils.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ModuleDatabase {

    /**
     * Database
     */

    @Provides
    @Singleton
    fun provideDataBase(context: Context): AppDataBase =
        Room.databaseBuilder(context, AppDataBase::class.java, Constants.DB_NAME).build()

    @Provides
    @Singleton
    fun provideTagDao(database: AppDataBase): TagDao =
        database.tagDAO

    @Provides
    @Singleton
    fun provideArticleDao(database: AppDataBase): ArticleDao =
        database.articleDAO

    @Provides
    @Singleton
    fun provideTransactionDao(database: AppDataBase): TransactionDao =
        database.transactionDao

    /**
     * Local Data
     */

    @Provides
    @Singleton
    fun provideLocalData(): LocalData =
        LocalData(LinkedHashMap())
}
