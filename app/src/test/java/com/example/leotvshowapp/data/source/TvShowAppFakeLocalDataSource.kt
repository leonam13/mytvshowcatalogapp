package com.example.leotvshowapp.data.source

import androidx.paging.PagingSource
import com.example.leotvshowapp.data.model.TvShow
import com.example.leotvshowapp.data.source.local.TvShowAppLocalDataSource
import kotlinx.coroutines.flow.Flow

class TvShowAppFakeLocalDataSource : TvShowAppLocalDataSource {

    override suspend fun saveFavoriteTvShow(showId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteTvShows(query: String): Flow<List<TvShow>> {
        TODO("Not yet implemented")
    }

    override suspend fun removeFavoriteTvShow(showId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun insertAll(shows: List<TvShow>) {
        TODO("Not yet implemented")
    }

    override fun searchShowsPagingSource(query: String): PagingSource<Int, TvShow> {
        TODO("Not yet implemented")
    }

    override suspend fun searchShows(query: String): List<TvShow> {
        TODO("Not yet implemented")
    }

    override suspend fun clearAll() {
        TODO("Not yet implemented")
    }

    override fun isFavorite(showId: Int): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun isShowEmpty(): Boolean {
        TODO("Not yet implemented")
    }
}