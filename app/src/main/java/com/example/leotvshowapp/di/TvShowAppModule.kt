package com.example.leotvshowapp.di

import com.example.leotvshowapp.data.repository.TvShowAppRepository
import com.example.leotvshowapp.data.repository.TvShowAppRepositoryImpl
import com.example.leotvshowapp.data.source.local.TvShowAppDatabase
import com.example.leotvshowapp.data.source.local.TvShowAppLocalDataSourceImpl
import com.example.leotvshowapp.data.source.remote.TvShowAppAPI
import com.example.leotvshowapp.data.source.remote.TvShowAppRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TvShowAppModule {

    @Provides
    @Singleton
    fun provideTvShowAPI(retrofit: Retrofit): TvShowAppAPI = retrofit.create(TvShowAppAPI::class.java)

    @Provides
    @Singleton
    fun provideTvShowRemoteData(api: TvShowAppAPI): TvShowAppRemoteDataSourceImpl = TvShowAppRemoteDataSourceImpl(api)

    @Provides
    @Singleton
    fun provideTvShowLocalDataSource(database: TvShowAppDatabase): TvShowAppLocalDataSourceImpl =
        TvShowAppLocalDataSourceImpl(database.tvShowDao())

    @Provides
    @Singleton
    fun provideTvShowRepository(remoteDataSource: TvShowAppRemoteDataSourceImpl,
                                localDataSource: TvShowAppLocalDataSourceImpl): TvShowAppRepository =
        TvShowAppRepositoryImpl(remoteDataSource, localDataSource)
}