package com.example.test.di.components

import com.example.test.di.modules.*
import com.example.test.ui.activities.HistoryActivity
import com.example.test.ui.activities.SearchActivity
import com.example.test.ui.activities.SearchResultActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ModuleApp::class,
        ModuleData::class,
        ModuleDatabase::class,
        ModuleModel::class,
        ModuleNetwork::class,
        ModuleService::class,
        ModuleViewModel::class
    ]
)
interface AppComponent {
    fun inject(historyActivity: HistoryActivity)
    fun inject(searchActivity: SearchActivity)
    fun inject(searchResultActivity: SearchResultActivity)
}
