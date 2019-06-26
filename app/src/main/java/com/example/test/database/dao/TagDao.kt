package com.example.test.database.dao

import android.arch.persistence.room.*
import com.example.test.database.entities.Tag
import io.reactivex.Single

@Dao
interface TagDao {

    @Query("SELECT * FROM tag")
    fun getAll(): Single<MutableList<Tag>>

    @Query("SELECT * FROM tag WHERE id = :id")
    fun getById(id: Long): Single<Tag>

    @Query("SELECT * FROM tag WHERE value = :value")
    fun getByValue(value: String): Single<Tag>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tag: Tag): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tags: MutableList<Tag>): MutableList<Long>

    @Update
    fun update(tag: Tag)

    @Delete
    fun delete(tag: Tag)
}
