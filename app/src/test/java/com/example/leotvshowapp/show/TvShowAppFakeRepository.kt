package com.example.leotvshowapp.show

import androidx.paging.PagingData
import com.example.leotvshowapp.data.model.Episode
import com.example.leotvshowapp.data.model.Person
import com.example.leotvshowapp.data.model.TvShow
import com.example.leotvshowapp.data.repository.TvShowAppRepository
import com.example.leotvshowapp.data.source.TvShowAppFakeRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TvShowAppFakeRepository : TvShowAppRepository {

    val remoteDS: TvShowAppFakeRemoteDataSource = TvShowAppFakeRemoteDataSource()

    override suspend fun getShows(query: String): Flow<PagingData<TvShow>> {
        return flow {
            emit(PagingData.from(remoteDS.searchShows(query)))
        }
    }

    override suspend fun getEpisodes(showId: Int): List<Map.Entry<Int, List<Episode>>> = remoteDS.getEpisodes(showId)

    override suspend fun searchPeople(query: String): Flow<PagingData<Person>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPersonCastCredits(personId: Int): List<TvShow> {
        TODO("Not yet implemented")
    }

    override suspend fun saveFavoriteTvShow(showId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteTvShows(query: String): Flow<List<TvShow>> {
        TODO("Not yet implemented")
    }

    override suspend fun removeFavoriteTvShow(showId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun isFavorite(showId: Int): Flow<Boolean> {
        TODO("Not yet implemented")
    }
}