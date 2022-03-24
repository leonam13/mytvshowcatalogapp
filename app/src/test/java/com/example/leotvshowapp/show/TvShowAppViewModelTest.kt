package com.example.leotvshowapp.show

import androidx.paging.PagingData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.leotvshowapp.data.model.TvShow
import com.example.leotvshowapp.data.repository.Result
import com.example.leotvshowapp.data.repository.TvShowAppRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.not
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TvShowAppViewModelTest {

    private val repository: TvShowAppRepository = TvShowAppFakeRepository()

    private val viewModel: TvShowAppViewModel = TvShowAppViewModel(repository)

    @Test
    fun setShowQuery_triggerShowResultList_returnSuccess() {
        runBlocking {
            viewModel.setShowQuery("")
            val item: Result<PagingData<TvShow>> = viewModel.showResultList.first()
            assertThat(item, instanceOf(Result.Success::class.java))
        }
    }

    @Test
    fun setShowQuery_changeMutableSharedFlow_returnLove() {
        runBlocking {
            viewModel.setShowQuery("love")
            val query = viewModel.getShowQuery().first()
            assertThat(query, equalTo("love"))
        }
    }

    @Test
    fun setShowQuery_changeMutableSharedFlow_notReturnLove() {
        runBlocking {
            viewModel.setShowQuery("loveless")
            val query = viewModel.getShowQuery().first()
            assertThat(query, not(equalTo("love")))
        }
    }

    @Test
    fun getEpisodes_returnLoadingAndSuccess() {
        runBlocking {
            val result = viewModel.getEpisodes(0).take(2).toList()
            assertThat(result[0], instanceOf(Result.Loading::class.java))
            assertThat(result[1], instanceOf(Result.Success::class.java))
        }
    }
}