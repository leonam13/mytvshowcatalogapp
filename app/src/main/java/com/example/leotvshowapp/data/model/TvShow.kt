package com.example.leotvshowapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.leotvshowapp.data.dto.Schedule
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class TvShow(
    @PrimaryKey
    val id: Int,
    val name: String,
    val posterUrl: String?,
    val schedule: Schedule?,
    val genres: List<String>,
    val summary: String?
) : Parcelable