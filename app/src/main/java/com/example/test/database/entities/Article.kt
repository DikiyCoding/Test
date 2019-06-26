package com.example.test.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey

@Entity(
    foreignKeys =
    [ForeignKey(
        entity = Tag::class,
        parentColumns = ["id"],
        childColumns = ["tag_id"],
        onDelete = CASCADE
    )]
)
class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var title: String,
    var description: String,
    var reference: String,
    @ColumnInfo(name = "image_url")
    var imageUrl: String,
    @ColumnInfo(name = "tag_id")
    var tagId: Long
)
