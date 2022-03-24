package com.example.leotvshowapp.data.dto

import com.example.leotvshowapp.data.model.Person

data class PersonDTO(
    val _links: LinksDTO,
    val birthday: String?,
    val country: CountryDTO,
    val deathday: String?,
    val gender: String,
    val id: Int,
    val image: ImageDTO?,
    val name: String,
    val updated: Int,
    val url: String
) {
    fun toModel() = Person(id = id, name = name, imageUrl = image?.medium)
}

