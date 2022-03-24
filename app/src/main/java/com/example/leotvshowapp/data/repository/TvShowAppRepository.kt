package com.example.leotvshowapp.data.repository

import androidx.paging.PagingData
import com.example.leotvshowapp.data.model.Episode
import com.example.leotvshowapp.data.model.Person
import com.example.leotvshowapp.data.model.TvShow
import kotlinx.coroutines.flow.Flow

interface TvShowAppRepository {

    suspend fun getShows(query: String): Flow<PagingData<TvShow>>

    suspend fun getEpisodes(showId: Int): List<Map.Entry<Int, List<Episode>>>

    suspend fun searchPeople(query: String): Flow<PagingData<Person>>

    suspend fun getPersonCastCredits(personId: Int): List<TvShow>

    suspend fun saveFavoriteTvShow(showId: Int)

    suspend fun getFavoriteTvShows(query: String): Flow<List<TvShow>>

    suspend fun removeFavoriteTvShow(showId: Int)

    suspend fun isFavorite(showId: Int): Flow<Boolean>
}