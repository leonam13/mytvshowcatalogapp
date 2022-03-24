package com.example.leotvshowapp.data.dto

data class LinksDTO(
    val previousepisode: HRef,
    val self: HRef
)

data class HRef(
    val href: String
)