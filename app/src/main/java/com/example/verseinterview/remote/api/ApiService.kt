package com.example.verseinterview.remote.api

import com.example.myapplication.domain.entities.ExchangeRates
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * The main services that handles all endpoint processes
 */
interface ApiService {

    @GET("latest")
    suspend fun getExchangeRates(): ExchangeRates

    @GET("latest")
    suspend fun changeBaseExchangeRates(@Query("base") base: String): ExchangeRates
}