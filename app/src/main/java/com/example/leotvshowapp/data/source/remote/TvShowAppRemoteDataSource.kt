package com.example.leotvshowapp.data.source.remote

import androidx.paging.Pager
import com.example.leotvshowapp.data.dto.TvShowDTO
import com.example.leotvshowapp.data.model.Episode
import com.example.leotvshowapp.data.model.Person
import com.example.leotvshowapp.data.model.TvShow
import retrofit2.Response

interface TvShowAppRemoteDataSource {

    suspend fun getShows(page: Int): Response<List<TvShowDTO>>

    suspend fun searchShows(query: String): List<TvShow>

    suspend fun getEpisodes(showId: Int): List<Map.Entry<Int, List<Episode>>>

    fun getPeople(): Pager<Int, Person>

    suspend fun searchPeople(query: String): List<Person>

    suspend fun getPersonCastCredits(personId: Int): List<TvShow>
}