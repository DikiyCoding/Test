package com.example.test.di.modules

import com.example.test.models.HistoryModel
import com.example.test.models.SearchModel
import com.example.test.models.SearchResultModel
import com.example.test.utils.ViewModelFactories.SearchViewModelFactory
import com.example.test.utils.ViewModelFactories.SearchResultViewModelFactory
import com.example.test.utils.ViewModelFactories.HistoryViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ModuleViewModel {

    @Provides
    @Singleton
    fun provideSearchViewModelFactory(model: SearchModel)
            : SearchViewModelFactory =
        SearchViewModelFactory(model)

    @Provides
    @Singleton
    fun provideHistoryViewModelFactory(model: HistoryModel)
            : HistoryViewModelFactory =
        HistoryViewModelFactory(model)

    @Provides
    @Singleton
    fun provideSearchResultViewModelFactory(model: SearchResultModel)
            : SearchResultViewModelFactory =
        SearchResultViewModelFactory(model)
}
