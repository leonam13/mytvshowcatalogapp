package com.example.leotvshowapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.leotvshowapp.data.model.Episode
import com.example.leotvshowapp.data.model.Person
import com.example.leotvshowapp.data.model.TvShow
import com.example.leotvshowapp.data.source.local.TvShowAppLocalDataSource
import com.example.leotvshowapp.data.source.local.TvShowAppLocalDataSourceImpl
import com.example.leotvshowapp.data.source.remote.TvShowAppRemoteDataSource
import com.example.leotvshowapp.data.source.remote.TvShowAppRemoteDataSourceImpl
import com.example.leotvshowapp.data.source.remote.TvShowRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TvShowAppRepositoryImpl @Inject constructor(
    private val remoteDS: TvShowAppRemoteDataSource,
    private val localDS: TvShowAppLocalDataSource
) : TvShowAppRepository {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getShows(query: String): Flow<PagingData<TvShow>> {
        return Pager(
            config = PagingConfig(15),
            remoteMediator = TvShowRemoteMediator(remoteDS, localDS, query)
        ) { localDS.searchShowsPagingSource(query) }.flow
    }

    override suspend fun getEpisodes(showId: Int): List<Map.Entry<Int, List<Episode>>> = remoteDS.getEpisodes(showId)

    override suspend fun searchPeople(query: String): Flow<PagingData<Person>> {
        return if (query.isEmpty()) remoteDS.getPeople().flow
        else flow { emit(PagingData.from(remoteDS.searchPeople(query))) }
    }

    override suspend fun getPersonCastCredits(personId: Int): List<TvShow> = remoteDS.getPersonCastCredits(personId)

    override suspend fun saveFavoriteTvShow(showId: Int) = localDS.saveFavoriteTvShow(showId)

    override suspend fun getFavoriteTvShows(query: String): Flow<List<TvShow>> = localDS.getFavoriteTvShows(query)

    override suspend fun removeFavoriteTvShow(showId: Int) = localDS.removeFavoriteTvShow(showId)

    override suspend fun isFavorite(showId: Int): Flow<Boolean> = localDS.isFavorite(showId)
}