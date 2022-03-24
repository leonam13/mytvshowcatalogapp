package com.example.leotvshowapp.show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.leotvshowapp.data.model.Episode
import com.example.leotvshowapp.data.model.TvShow
import com.example.leotvshowapp.data.repository.Result
import com.example.leotvshowapp.data.repository.TvShowAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowAppViewModel @Inject constructor(private val repository: TvShowAppRepository) : ViewModel() {

    private val showQuery = MutableSharedFlow<String>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    ).apply { tryEmit("") }

    @ExperimentalCoroutinesApi
    val showResultList: Flow<Result<PagingData<TvShow>>> = showQuery
        .onStart { Result.Loading }
        .flatMapLatest { query -> repository.getShows(query) }
        .map { Result.Success(it) as Result<PagingData<TvShow>> }
        .catch {
            it.printStackTrace()
            emit(Result.Error(it))
        }

    fun setShowQuery(text: String?) {
        showQuery.tryEmit(text.orEmpty())
    }

    fun getShowQuery() = showQuery

    fun getEpisodes(showId: Int): Flow<Result<List<Map.Entry<Int, List<Episode>>>>> {
        return flow {
            emit(Result.Loading)
            runCatching { repository.getEpisodes(showId) }
                .onSuccess { emit(Result.Success(it)) }
                .onFailure {
                    it.printStackTrace()
                    emit(Result.Error(it))
                }
        }
    }

    fun updateFavorites(checked: Boolean, showId: Int) {
        viewModelScope.launch {
            if (checked) repository.saveFavoriteTvShow(showId)
            else repository.removeFavoriteTvShow(showId)
        }
    }

    suspend fun isFavorite(showId: Int) = repository.isFavorite(showId)
}