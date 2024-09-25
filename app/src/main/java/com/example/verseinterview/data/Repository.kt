package com.example.verseinterview.data

import com.example.myapplication.domain.entities.ExchangeRates
import com.example.verseinterview.base.reources.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Methods of Repository
 */
interface Repository {

    suspend fun getExchangesRate(): Flow<Resource<ExchangeRates>>
    suspend fun changeBaseExchangesRate(base: String): Flow<Resource<ExchangeRates>>
}