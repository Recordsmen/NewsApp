package com.example.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DataBaseNews constructor(
    @PrimaryKey
    val id: String,
    val category: String,
    val country: String,
    val description: String,
    val language: String,
    val name: String,
    val url: String,
    val isStarred: Boolean
)