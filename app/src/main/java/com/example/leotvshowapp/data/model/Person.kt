package com.example.leotvshowapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(val id: Int, val name: String, val imageUrl: String?) : Parcelable