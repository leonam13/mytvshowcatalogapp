package com.example.leotvshowapp.data.source.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.leotvshowapp.NoMorePagesException
import com.example.leotvshowapp.data.model.TvShow
import com.example.leotvshowapp.data.source.local.TvShowAppLocalDataSource

private const val TV_SHOW_FIRST_PAGE = 0

private const val TV_SHOW_API_LIMIT = 250

@OptIn(ExperimentalPagingApi::class)
class TvShowRemoteMediator(
    private val remoteDS: TvShowAppRemoteDataSource,
    private val localDS: TvShowAppLocalDataSource,
    private val query: String
) : RemoteMediator<Int, TvShow>() {

    override suspend fun initialize(): InitializeAction {
        return if (localDS.isShowEmpty() || localDS.searchShows(query).isEmpty()) InitializeAction.LAUNCH_INITIAL_REFRESH
        else InitializeAction.SKIP_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, TvShow>): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull() ?: return MediatorResult.Success(endOfPaginationReached = true)
                    (lastItem.id / TV_SHOW_API_LIMIT) + 1
                }
            }

            if (query.isNotEmpty()) {
                val r = remoteDS.searchShows(query)
                localDS.insertAll(r)
                MediatorResult.Success(endOfPaginationReached = true)
            } else {
                val response = remoteDS.getShows(loadKey ?: TV_SHOW_FIRST_PAGE)
                if (loadType == LoadType.REFRESH) localDS.clearAll()

                if (response.code() != NoMorePagesException.code)
                    localDS.insertAll(response.body()?.map { it.toModel() } ?: emptyList())
                MediatorResult.Success(endOfPaginationReached = response.code() == NoMorePagesException.code)
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}