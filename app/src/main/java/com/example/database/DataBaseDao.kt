package com.example.database

import androidx.room.*
import com.example.model.Source


@Dao
interface DataBaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg news: DataBaseNews)

    @Query("SELECT * FROM DataBaseNews")
    fun showAllNews():List<Source>

    @Query("SELECT * FROM DataBaseNews WHERE category == :category")
    fun showNewsSortedByCategory(category:String):List<Source>

    @Query("SELECT * FROM DataBaseNews WHERE isFavorite == 1")
    fun getAllFavoriteNews():List<Source>

    @Query("UPDATE DataBaseNews SET isFavorite = :isFavorite WHERE id=:id")
    fun setArticleIsFavorite(id: String, isFavorite: Boolean)
}