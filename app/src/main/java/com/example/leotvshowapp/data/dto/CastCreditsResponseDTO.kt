package com.example.leotvshowapp.data.dto

data class CastCreditsResponseDTO(
    val self: Boolean,
    val voice: Boolean,
    val _links: Any?,
    val _embedded: CastCreditsResponseEmbedded?
)

data class CastCreditsResponseEmbedded(val show: TvShowDTO)