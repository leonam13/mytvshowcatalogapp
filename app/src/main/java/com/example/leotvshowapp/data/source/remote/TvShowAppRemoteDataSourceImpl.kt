package com.example.leotvshowapp.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.leotvshowapp.data.dto.TvShowDTO
import com.example.leotvshowapp.data.model.Episode
import com.example.leotvshowapp.data.model.Person
import com.example.leotvshowapp.data.model.TvShow
import retrofit2.Response
import javax.inject.Inject

class TvShowAppRemoteDataSourceImpl @Inject constructor(private val service: TvShowAppAPI) : TvShowAppRemoteDataSource {

    override suspend fun getShows(page: Int): Response<List<TvShowDTO>> = service.getShows(page)

    override suspend fun searchShows(query: String): List<TvShow> = service.searchShow(query).map { it.show.toModel() }

    override suspend fun getEpisodes(showId: Int): List<Map.Entry<Int, List<Episode>>> = service.getShowEpisodes(showId)
        .map { it.toModel() }
        .groupBy { it.season }
        .entries
        .toList()

    override fun getPeople(): Pager<Int, Person> = Pager(
        config = PagingConfig(pageSize = 250, prefetchDistance = 2),
        pagingSourceFactory = { PeoplePagingSource(service) }
    )

    override suspend fun searchPeople(query: String): List<Person> = service.searchPeople(query).map { it.person.toModel() }

    override suspend fun getPersonCastCredits(personId: Int): List<TvShow> = service.getPersonCastCredits(personId)
        .filter { it._embedded != null }
        .map { it._embedded!!.show.toModel() }
        .groupBy { it.id }
        .map { it.value.first() }
}