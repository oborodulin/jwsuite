package com.oborodulin.home.common.data.network

import com.oborodulin.home.common.domain.Result
import com.oborodulin.home.common.domain.usecases.UseCaseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class NetworkBoundResult<ResultType : Any, RequestType : Any> {
    private var result: Flow<Result<ResultType>> = flow {
        val dbSource = loadFromDB().first()
        if (shouldFetch(dbSource)) {
            when (val apiResponse = createCall().first()) {
                is ApiResponse.Success -> {
                    saveCallResult(apiResponse.data)
                    emitAll(loadFromDB().map { Result.Success(it) })
                }

                is ApiResponse.Empty -> {
                    emitAll(loadFromDB().map { Result.Success(it) })
                }

                is ApiResponse.Error -> {
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