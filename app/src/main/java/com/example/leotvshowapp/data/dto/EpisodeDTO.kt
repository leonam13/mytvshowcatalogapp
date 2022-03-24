package com.example.leotvshowapp.data.dto

import com.example.leotvshowapp.data.model.Episode

data class EpisodeDTO(
    val _links: LinksDTO,
    val airdate: String,
    val airstamp: String,
    val airtime: String,
    val id: Int,
    val image: ImageDTO?,
    val name: String,
    val number: Int,
    val rating: Rating,
    val runtime: Int,
    val season: Int,
    val summary: String?,
    val type: String,
    val url: String
) {
    fun toModel() = Episode(
        name = name,
        number = number,
        season = season,
        summary = summary.orEmpty(),
        image = image?.medium
    )
}