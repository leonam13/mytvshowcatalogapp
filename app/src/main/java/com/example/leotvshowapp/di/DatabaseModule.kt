package com.example.leotvshowapp.di

import android.content.Context
import androidx.room.Room
import com.example.leotvshowapp.data.source.local.TvShowAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): TvShowAppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            TvShowAppDatabase::class.java,
            "TvAppShowDatabase.db"
        ).fallbackToDestructiveMigration().build()
    }
}