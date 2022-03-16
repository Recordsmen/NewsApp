package com.example.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Source(
    val id: String,
    val country: String,
    val description: String,
    val category: String,
    val language: String,
    val name: String,
    val url: String,
    var isStarred:Boolean
    ):Parcelable