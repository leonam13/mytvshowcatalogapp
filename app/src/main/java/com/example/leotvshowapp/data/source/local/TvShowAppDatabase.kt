package com.example.leotvshowapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.leotvshowapp.data.model.FavoriteTvShow
import com.example.leotvshowapp.data.model.TvShow

private const val DATABASE_VERSION = 1

@Database(
    entities = [TvShow::class, FavoriteTvShow::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TvShowAppDatabase : RoomDatabase() {

    abstract fun tvShowDao(): TvShowDao
}