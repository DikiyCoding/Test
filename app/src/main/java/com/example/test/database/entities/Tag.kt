package com.example.test.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Tag(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var value: String,
    @ColumnInfo(name = "image_url")
    var imageUrl: String
)
