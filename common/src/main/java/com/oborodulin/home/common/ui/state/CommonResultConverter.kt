package com.oborodulin.home.common.ui.state

import com.oborodulin.home.common.BuildConfig
import com.oborodulin.home.common.domain.entities.Result

abstract class CommonResultConverter<T : Any, R : Any> {

    fun convert(result: Result<T>): UiState<R> {
        return when (result) {
            is Result.Error -> {
                if (BuildConfig.DEBUG) {
                    UiState.Error(result.exception.stackTraceToString())
                } else {
                    UiState.Error(result.exception.localizedMessage.orEmpty())
                }
            }

            is Result.Success -> {
                UiState.Success(convertSuccess(result.data))
            }
        }
    }

    abstract fun convertSuccess(data: T): R
}