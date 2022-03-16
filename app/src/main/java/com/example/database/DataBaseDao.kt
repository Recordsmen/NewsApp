package com.example.database

import androidx.room.*
import com.example.model.Source


@Dao
interface DataBaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg news: DataBaseNews)

    @Query("SELECT * FROM DataBaseNews")
    fun getAllNews():List<Source>

    @Query("SELECT * FROM DataBaseNews WHERE category == :category")
    fun getCategoryNews(category:String):List<Source>

    @Query("SELECT * FROM DataBaseNews WHERE isStarred == 1")
    fun getAllStarredNews():List<Source>

    @Query("UPDATE DataBaseNews SET isStarred = :isStarred WHERE id=:id")
    fun setNewsIsStarred(id: String, isStarred: Boolean)
}