package com.oborodulin.home.common.data.network

sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val cause: Throwable) : ApiResponse<Nothing>()
    data object Empty : ApiResponse<Nothing>()
}