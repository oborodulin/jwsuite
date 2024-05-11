package com.oborodulin.home.common.data.network

import com.oborodulin.home.common.domain.Result
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.home.common.util.LogLevel.LOG_NETWORK
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

private const val TAG = "Common.NetworkBoundResult"

abstract class NetworkBoundResult<ResultType : Any, RequestType : Any> {
    private var result: Flow<Result<ResultType>> = flow {
        val dbSource = loadFromDB().first()
        val isFetch = shouldFetch(dbSource);
        if (LOG_NETWORK) {
            Timber.tag(TAG).d("NetworkBoundResult: isFetch = %s; dbSource = %s", isFetch, dbSource)
        }
        if (isFetch) {
            when (val apiResponse = createCall().first()) {
                is ApiResponse.Success -> {
                    saveCallResult(apiResponse.data)
                    if (LOG_NETWORK) {
                        Timber.tag(TAG).d("saved Call Result: data = %s", apiResponse.data)
                    }
                    emitAll(loadFromDB().map { Result.Success(it) })
                }

                is ApiResponse.Empty -> {
                    if (LOG_NETWORK) {
                        Timber.tag(TAG).d("ApiResponse is Empty")
                    }
                    emitAll(loadFromDB().map { Result.Success(it) })
                }

                is ApiResponse.Error -> {
                    if (LOG_NETWORK) {
                        Timber.tag(TAG).e(apiResponse.cause)
                    }
                    onFetchFailed()
                    emit(Result.Error(UseCaseException.createFromThrowable(apiResponse.cause)))
                }
            }
        } else {
            emitAll(loadFromDB().map { Result.Success(it) })
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<Result<ResultType>> = result
}