package com.example.leotvshowapp.data.dto

import android.os.Parcelable
import com.example.leotvshowapp.data.model.TvShow
import kotlinx.parcelize.Parcelize

data class TvShowDTO(
    val _links: LinksDTO,
    val averageRuntime: Int,
    val dvdCountry: Any?,
    val ended: String,
    val externals: Externals,
    val genres: List<String>,
    val id: Int,
    val image: ImageDTO?,
    val language: String,
    val name: String,
    val network: Network,
    val officialSite: String,
    val premiered: String,
    val rating: Rating,
    val runtime: Int,
    val schedule: Schedule,
    val status: String,
    val summary: String?,
    val type: String,
    val updated: Int,
    val url: String,
    val webChannel: Any?,
    val weight: Int
) {
    fun toModel() = TvShow(
        id = id,
        name = name,
        posterUrl = image?.medium,
        schedule = schedule,
        genres = genres,
        summary = summary
    )
}

data class Externals(
    val imdb: String,
    val thetvdb: Int,
    val tvrage: Int
)

data class Network(
    val country: CountryDTO,
    val id: Int,
    val name: String
)

data class Rating(val average: Double)

@Parcelize
data class Schedule(
    val days: List<String>,
    val time: String
) : Parcelable {
    override fun toString(): String {
        if (days.isEmpty()) return ""
        return days.joinToString().plus(" at $time")
    }
}