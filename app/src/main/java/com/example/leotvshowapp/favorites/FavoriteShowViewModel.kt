package com.example.leotvshowapp.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class FavoriteShowViewModel @Inject constructor(private val repository: TvShowAppRepository) : ViewModel() {

    private val showQuery = MutableSharedFlow<String>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    ).apply { tryEmit("") }

    @ExperimentalCoroutinesApi
    val searchShowResult: Flow<Result<MutableList<TvShow>>> = showQuery
        .flatMapLatest { query -> repository.getFavoriteTvShows(query) }
        .map { Result.Success(it.toMutableList()) as Result<MutableList<TvShow>> }
        .catch {
            it.printStackTrace()
            emit(Result.Error(it))
        }

    fun setShowQuery(text: String?) {
        showQuery.tryEmit(text.orEmpty())
    }

    fun removeFromFavorites(showId: Int) {
        viewModelScope.launch {
            repository.removeFavoriteTvShow(showId)
        }
    }
}