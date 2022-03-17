package com.example.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Source(
    val id: String,
    val category: String,
    val country: String,
    val description: String,
    val language: String,
    val name: String,
    val url: String,
    var isFavorite:Boolean
    ):Parcelable