package com.example.leotvshowapp.people

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.leotvshowapp.data.model.Person
import com.example.leotvshowapp.data.model.TvShow
import com.example.leotvshowapp.data.repository.Result
import com.example.leotvshowapp.data.repository.TvShowAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(private val repository: TvShowAppRepository) : ViewModel() {

    private val personQuery = MutableSharedFlow<String>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    ).apply { tryEmit("") }

    @ExperimentalCoroutinesApi
    val searchPersonResult: Flow<Result<PagingData<Person>>> = personQuery
        .flatMapLatest { query -> repository.searchPeople(query) }
        .map { Result.Success(it) as Result<PagingData<Person>> }
        .catch { exception ->
            exception.printStackTrace()
            emit(Result.Error(exception))
        }

    fun setPersonQuery(text: String?) {
        personQuery.tryEmit(text.orEmpty())
    }

    fun getPersonCastCredits(personId: Int): Flow<Result<List<TvShow>>> {
        return flow {
            emit(Result.Loading)
            runCatching { repository.getPersonCastCredits(personId) }
                .onSuccess { emit(Result.Success(it)) }
                .onFailure { emit(Result.Error(it)) }
        }
    }
}