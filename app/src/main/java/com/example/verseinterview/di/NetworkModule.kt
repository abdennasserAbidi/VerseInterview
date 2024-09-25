package com.example.verseinterview.di

import com.example.verseinterview.remote.api.ApiService
import com.example.verseinterview.remote.service.NetworkModuleFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton

/**
 * Module that holds Network related classes
 */
@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideServiceEndPoint(): ApiService = NetworkModuleFactory.makeService()
}