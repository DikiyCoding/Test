package com.example.test.utils

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.annotation.NonNull
import com.example.test.models.HistoryModel
import com.example.test.models.SearchModel
import com.example.test.models.SearchResultModel
import com.example.test.viewmodels.HistoryViewModel
import com.example.test.viewmodels.SearchResultViewModel
import com.example.test.viewmodels.SearchViewModel

object ViewModelFactories {

    class HistoryViewModelFactory(private val model: HistoryModel)
        : ViewModelProvider.NewInstanceFactory() {
        @NonNull
        override fun <T : ViewModel> create(@NonNull modelClass: Class<T>): T {
            if (modelClass == HistoryViewModel::class.java)
                return HistoryViewModel(model) as T
            return modelClass.newInstance() //stub for notnull
        }
    }

    class SearchViewModelFactory(private val model: SearchModel)
        : ViewModelProvider.NewInstanceFactory() {
        @NonNull
        override fun <T : ViewModel> create(@NonNull modelClass: Class<T>): T {
            if (modelClass == SearchViewModel::class.java)
                return SearchViewModel(model) as T
            return modelClass.newInstance() //stub for notnull
        }
    }

    class SearchResultViewModelFactory(private val model: SearchResultModel)
        : ViewModelProvider.NewInstanceFactory() {
        @NonNull
        override fun <T : ViewModel> create(@NonNull modelClass: Class<T>): T {
            if (modelClass == SearchResultViewModel::class.java)
                return SearchResultViewModel(model) as T
            return modelClass.newInstance() //stub for notnull
        }
    }
}
