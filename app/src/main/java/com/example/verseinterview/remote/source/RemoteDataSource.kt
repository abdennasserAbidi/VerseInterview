package com.example.verseinterview.remote.source

import com.example.verseinterview.domain.entities.ExchangeRates

/**
 * Methods of Remote Data Source
 */
interface RemoteDataSource {

    suspend fun getExchangesRate(): ExchangeRates
    suspend fun changeBaseExchangesRate(base: String): ExchangeRates
}