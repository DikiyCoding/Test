package com.example.test.di.modules

import com.example.test.data.LocalData
import com.example.test.models.*
import com.example.test.network.other.JSoupHandler
import com.example.test.service.CacheService
import com.example.test.service.ImagesService
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ModuleModel {

    @Provides
    @Singleton
    fun provideDetailsModel()
            : DetailsModel = DetailsModel()

    @Provides
    @Singleton
    fun provideHistoryModel(
        cacheService: CacheService,
        localData: LocalData
    ): HistoryModel = HistoryModel(cacheService, localData)

    @Provides
    @Singleton
    fun provideMenuModel()
            : MenuModel = MenuModel()

    @Provides
    @Singleton
    fun provideSearchModel(
        localData: LocalData,
        @Named("Search Tags") tags: MutableList<Pair<String, String>>
        ): SearchModel = SearchModel(localData, tags)

    @Provides
    @Singleton
    fun provideSearchResultModel(
        handler: JSoupHandler,
        cacheService: CacheService,
        imagesService: ImagesService,
        localData: LocalData
    ): SearchResultModel = SearchResultModel(
        handler,
        cacheService,
        imagesService,
        localData
    )
}
