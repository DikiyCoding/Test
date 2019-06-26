package com.example.test.data

import com.example.test.database.entities.Article
import com.example.test.database.entities.Tag

class LocalData(
    val history: MutableMap<Tag, MutableList<Article>>
)
