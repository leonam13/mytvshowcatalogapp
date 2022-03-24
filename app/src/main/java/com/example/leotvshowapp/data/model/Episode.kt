package com.example.leotvshowapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Episode(
    val name: String,
    val number: Int,
    val season: Int,
    val summary: String,
    val image: String?
) : Parcelable