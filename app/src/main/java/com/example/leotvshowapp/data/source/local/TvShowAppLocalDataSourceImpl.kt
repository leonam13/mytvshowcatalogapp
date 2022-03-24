package com.example.leotvshowapp.data.source.local

import androidx.paging.PagingSource
import com.example.leotvshowapp.data.model.FavoriteTvShow
import com.example.leotvshowapp.data.model.TvShow
import kotlinx.coroutines.flow.Flow

class TvShowAppLocalDataSourceImpl(private val tvShowDao: TvShowDao) : TvShowAppLocalDataSource {

    override suspend fun saveFavoriteTvShow(showId: Int) = tvShowDao.saveFavoriteTvShow(FavoriteTvShow(showId))

    override suspend fun getFavoriteTvShows(query: String): Flow<List<TvShow>> {
        val favoriteIds = tvShowDao.getFavoriteTvShowsId()
        val q: String = if (query.isNotBlank()) "%$query%" else "%"
        return tvShowDao.getFavoriteTvShows(favoriteIds, q)
    }

    override suspend fun removeFavoriteTvShow(showId: Int) = tvShowDao.removeFavoriteTvShow(showId)

    override suspend fun insertAll(shows: List<TvShow>) = tvShowDao.insertAll(shows)

    override fun searchShowsPagingSource(query: String): PagingSource<Int, TvShow> {
        val q: String = if (query.isNotBlank()) "%$query%" else "%"
        return tvShowDao.searchShowsPagingSource(q)
    }

    override suspend fun searchShows(query: String): List<TvShow> {
        val q: String = if (query.isNotBlank()) "%$query%" else "%"
        return tvShowDao.searchShows(q)
    }

    override suspend fun clearAll() = tvShowDao.clearAll()

    override fun isFavorite(showId: Int): Flow<Boolean> = tvShowDao.isFavorite(showId)

    override suspend fun isShowEmpty(): Boolean = tvShowDao.getShowsCount() == 0
}