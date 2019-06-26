package com.example.test.di.modules

import com.example.test.database.dao.ArticleDao
import com.example.test.database.dao.TagDao
import com.example.test.database.dao.TransactionDao
import com.example.test.network.apis.service.GoogleSearchApi
import com.example.test.service.CacheService
import com.example.test.service.ImagesService
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ModuleService {

    @Provides
    @Singleton
    fun provideCacheService(
        tagDao: TagDao,
        articleDao: ArticleDao,
        transactionDao: TransactionDao
    ): CacheService =
        CacheService(
            tagDao,
            articleDao,
            transactionDao
        )

    @Provides
    @Singleton
    fun provideImagesService(
        api: GoogleSearchApi,
        @Named("Google Search Parameters") params: MutableMap<String, String>
    ): ImagesService =
        ImagesService(api, params)
}
