package com.example.leotvshowapp.data.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.leotvshowapp.data.source.TvShowAppFakeLocalDataSource
import com.example.leotvshowapp.data.source.TvShowAppFakeRemoteDataSource
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TvShowAppRepositoryImplTest {

    private val repository = TvShowAppRepositoryImpl(TvShowAppFakeRemoteDataSource(), TvShowAppFakeLocalDataSource())

    @Test
    fun getEpisodes_returnPilot() {
        runBlocking {
            val episode = repository.getEpisodes(0).first().value.first()
            assertThat(episode.name, equalTo("Pilot"))
        }
    }
}