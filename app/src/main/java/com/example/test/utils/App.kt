package com.example.test.utils

import android.app.Application
import com.example.test.di.components.AppComponent
import com.example.test.di.components.DaggerAppComponent
import com.example.test.di.modules.*

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .moduleApp(ModuleApp(this))
            .moduleData(ModuleData())
            .moduleDatabase(ModuleDatabase())
            .moduleModel(ModuleModel())
            .moduleNetwork(ModuleNetwork())
            .moduleService(ModuleService())
            .moduleViewModel(ModuleViewModel())
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}
