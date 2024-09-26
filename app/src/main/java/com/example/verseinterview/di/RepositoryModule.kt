package com.example.verseinterview.di

import com.example.verseinterview.remote.source.RemoteDataSource
import com.example.verseinterview.remote.source.RemoteDataSourceImp
import com.example.verseinterview.data.Repository
import com.example.verseinterview.data.RepositoryImp
import com.example.verseinterview.local.source.LocalDataSource
import com.example.verseinterview.local.source.LocalDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Module that holds Repository classes
 */
@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideLocalDataSource(localDataSourceImpl: LocalDataSourceImp): LocalDataSource

    @Binds
    abstract fun provideRemoteDataSource(remoteDataSourceImp: RemoteDataSourceImp): RemoteDataSource

    @Binds
    @ViewModelScoped
    abstract fun provideRepository(repository : RepositoryImp) : Repository

}