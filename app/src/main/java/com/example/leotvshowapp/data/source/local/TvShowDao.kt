package com.example.leotvshowapp.data.source.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.leotvshowapp.data.model.FavoriteTvShow
import com.example.leotvshowapp.data.model.TvShow
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavoriteTvShow(favoriteTvShow: FavoriteTvShow)

    @Query("SELECT * FROM (SELECT * from TvShow WHERE id in (:showIds)) WHERE name LIKE :query ORDER BY name")
    fun getFavoriteTvShows(showIds: List<Int>, query: String): Flow<List<TvShow>>

    @Query("SELECT id from FavoriteTvShow")
    suspend fun getFavoriteTvShowsId(): List<Int>

    @Query("DELETE FROM FavoriteTvShow WHERE id = :showId")
    suspend fun removeFavoriteTvShow(showId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(shows: List<TvShow>)

    @Query("SELECT * FROM TvShow WHERE name LIKE :query")
    fun searchShowsPagingSource(query: String): PagingSource<Int, TvShow>

    @Query("SELECT * FROM TvShow WHERE name LIKE :query")
    suspend fun searchShows(query: String): List<TvShow>

    @Query("DELETE FROM TvShow")
    suspend fun clearAll()

    @Query("SELECT COUNT(id)FROM FavoriteTvShow WHERE id = :showId == 1")
    fun isFavorite(showId: Int): Flow<Boolean>

    @Query("SELECT COUNT(id)FROM FavoriteTvShow")
    suspend fun getShowsCount(): Int
}