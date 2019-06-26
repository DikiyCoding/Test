package com.example.test.di.modules

import android.content.Context
import com.example.test.utils.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ModuleApp(private val app: App) {

    @Provides
    @Singleton
    fun provideApp(): Context = app
}
