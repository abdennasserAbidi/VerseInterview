package com.example.myapplication.data

import com.example.myapplication.remote.source.RemoteDataSource
import com.example.myapplication.base.reources.Resource
import com.example.myapplication.domain.entities.ExchangeRates
import com.example.verseinterview.local.source.LocalDataSource
import com.example.verseinterview.data.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Implementation class of [Repository]
 */
class RepositoryImp @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : Repository {

    override suspend fun getExchangesRate(): Flow<Resource<ExchangeRates>> = flow {
        try {
            // Get data from RemoteDataSource
            val data = remoteDataSource.getExchangesRate()
            // Emit data
            emit(Resource(ResourceState.SUCCESS, data, null))
        } catch (ex : Exception) {
            // Emit error
            emit(Resource(ResourceState.ERROR, null, ex.message))
        }
    }

    override suspend fun changeBaseExchangesRate(base: String): Flow<Resource<ExchangeRates>> = flow {
        try {
            // Get data from RemoteDataSource
            val data = remoteDataSource.changeBaseExchangesRate(base)
            // Emit data
            emit(Resource(ResourceState.SUCCESS, data, null))
        } catch (ex : Exception) {
            // Emit error
            emit(Resource(ResourceState.ERROR, null, ex.message))
        }
    }
}