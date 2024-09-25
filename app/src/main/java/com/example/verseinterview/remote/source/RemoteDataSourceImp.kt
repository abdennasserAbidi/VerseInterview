package com.example.verseinterview.remote.source

import com.example.myapplication.domain.entities.ExchangeRates
import com.example.verseinterview.remote.api.ApiService
import com.example.verseinterview.remote.source.RemoteDataSource
import javax.inject.Inject

/**
 * Implementation of [RemoteDataSource] class
 */
class RemoteDataSourceImp @Inject constructor(
    private val apiService : ApiService
    ) : RemoteDataSource {

    override suspend fun getExchangesRate(): ExchangeRates = apiService.getExchangeRates()
    override suspend fun changeBaseExchangesRate(base: String): ExchangeRates = apiService.changeBaseExchangeRates(base)
}