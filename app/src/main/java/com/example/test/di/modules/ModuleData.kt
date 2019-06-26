package com.example.test.di.modules

import com.example.test.utils.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ModuleData {

    @Provides
    @Singleton
    @Named("Google Search URL")
    fun provideGoogleSearchBaseUrlString()
            : String = Constants.GOOGLE_BASE_URL

    @Provides
    @Singleton
    @Named("Google Search Parameters")
    fun provideGoogleSearchRequestParameters(): MutableMap<String, String> {
        val params = HashMap<String, String>()
        params["key"] = Constants.GOOGLE_API_KEY
        params["cx"] = Constants.ENGINE_ID
        params["searchType"] = Constants.SEARCH_TYPE
        params["alt"] = Constants.RESPONSE_TYPE
        params["start"] = Constants.START_POINT_DEFAULT
        return params
    }

    @Provides
    @Singleton
    @Named("Search Tags")
    fun provideSearchTags(): MutableList<Pair<String, String>> {
        val tags = ArrayList<Pair<String, String>>()
        tags.add(Pair("Android", "android"))
        tags.add(Pair("C", "c-language"))
        tags.add(Pair("C#", "c-sharp"))
        tags.add(Pair("C++", "cpp"))
        tags.add(Pair("CSS", "css"))
        tags.add(Pair("HTML", "html"))
        tags.add(Pair("Java", "java"))
        tags.add(Pair("JavaScript", "javascript"))
        tags.add(Pair("Kotlin", "kotlin"))
        tags.add(Pair("Objective-C", "objective-c"))
        tags.add(Pair("PHP", "php"))
        tags.add(Pair("Python", "python"))
        tags.add(Pair("Ruby", "ruby"))
        tags.add(Pair("Scala", "scala"))
        tags.add(Pair("SQL", "sql"))
        tags.add(Pair("Swift", "swift"))
        return tags
    }
}
