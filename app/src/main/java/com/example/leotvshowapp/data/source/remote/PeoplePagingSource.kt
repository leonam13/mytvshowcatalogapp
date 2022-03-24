package com.example.leotvshowapp.data.source.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.leotvshowapp.NoMorePagesException
import com.example.leotvshowapp.PeopleNoMorePagesException
import com.example.leotvshowapp.data.model.Person

private const val PEOPLE_FIRST_PAGE = 0

class PeoplePagingSource(private val service: TvShowAppAPI) : PagingSource<Int, Person>() {

    override fun getRefreshKey(state: PagingState<Int, Person>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Person> {
        val pageNumber = params.key ?: PEOPLE_FIRST_PAGE

        return try {
            val response = service.getPeople(pageNumber)

            if (response.code() == NoMorePagesException.code)
                throw PeopleNoMorePagesException()

            LoadResult.Page(
                data = response.body()!!.map { it.toModel() },
                prevKey = if (pageNumber == PEOPLE_FIRST_PAGE) null else pageNumber - 1,
                nextKey = pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}