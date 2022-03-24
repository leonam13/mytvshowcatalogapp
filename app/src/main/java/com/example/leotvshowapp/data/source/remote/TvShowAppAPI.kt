package com.example.leotvshowapp.data.source.remote

import com.example.leotvshowapp.data.dto.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowAppAPI {

    @GET("/shows")
    suspend fun getShows(@Query("page") page: Int): Response<List<TvShowDTO>>

    @GET("/shows/{showId}/episodes")
    suspend fun getShowEpisodes(@Path("showId") showId: Int): List<EpisodeDTO>

    @GET("/search/shows")
    suspend fun searchShow(@Query("q") query: String): List<SearchShowResponseDTO>

    @GET("/people")
    suspend fun getPeople(@Query("page") pageNumber: Int): Response<List<PersonDTO>>

    @GET("/search/people")
    suspend fun searchPeople(@Query("q") query: String): List<SearchPersonResponseDTO>

    @GET("/people/{personId}/castcredits?embed=show")
    suspend fun getPersonCastCredits(@Path("personId") personId: Int): List<CastCreditsResponseDTO>
}
