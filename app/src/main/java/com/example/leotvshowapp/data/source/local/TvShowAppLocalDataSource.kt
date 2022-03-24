package com.example.leotvshowapp.data.source.local

import androidx.paging.PagingSource
import com.example.leotvshowapp.data.model.TvShow
import kotlinx.coroutines.flow.Flow

interface TvShowAppLocalDataSource {

    suspend fun saveFavoriteTvShow(showId: Int)

    suspend fun getFavoriteTvShows(query: String): Flow<List<TvShow>>

    suspend fun removeFavoriteTvShow(showId: Int)

    suspend fun insertAll(shows: List<TvShow>)

    fun searchShowsPagingSource(query: String): PagingSource<Int, TvShow>

    suspend fun searchShows(query: String): List<TvShow>

    suspend fun clearAll()

    fun isFavorite(showId: Int): Flow<Boolean>

    suspend fun isShowEmpty(): Boolean
}