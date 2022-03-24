package com.example.leotvshowapp.data.source

import androidx.paging.Pager
import com.example.leotvshowapp.data.dto.Schedule
import com.example.leotvshowapp.data.dto.TvShowDTO
import com.example.leotvshowapp.data.model.Episode
import com.example.leotvshowapp.data.model.Person
import com.example.leotvshowapp.data.model.TvShow
import com.example.leotvshowapp.data.source.remote.TvShowAppRemoteDataSource
import retrofit2.Response

class TvShowAppFakeRemoteDataSource : TvShowAppRemoteDataSource {

    override suspend fun getShows(page: Int): Response<List<TvShowDTO>> {
        TODO("Not yet implemented")
    }

    override suspend fun searchShows(query: String): List<TvShow> {
        return listOf(
            TvShow(0, "My Test Show", "", Schedule(listOf("monday"), "2pm"), listOf(), "")
        )
    }

    override suspend fun getEpisodes(showId: Int): List<Map.Entry<Int, List<Episode>>> {
        return listOf(Episode("Pilot", 1, 1, "", null)).groupBy { it.season }
            .entries
            .toList()
    }

    override fun getPeople(): Pager<Int, Person> {
        TODO("Not yet implemented")
    }

    override suspend fun searchPeople(query: String): List<Person> {
        TODO("Not yet implemented")
    }

    override suspend fun getPersonCastCredits(personId: Int): List<TvShow> {
        TODO("Not yet implemented")
    }
}