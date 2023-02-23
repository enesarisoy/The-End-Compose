package com.ns.theendcompose.data.api

sealed class ApiResponse<out T> {
    class Success<T>(val data: T?): ApiResponse<T>()
    class Failure<T>(val apiError: ApiError): ApiResponse<T>()
    class Exception<T>(val exception: Throwable): ApiResponse<T>()
}

fun <T> ApiResponse<T>.onSuccess(onResult: ApiResponse.Success<T>. () -> Unit): ApiResponse {
    if(this is ApiResponse.Success)
        onResult(this)
    return this
}

fun <T> ApiResponse<T>.onFailure(onResult: ApiResponse.Failure<*>. () -> Unit): ApiResponse {
    if(this is ApiResponse.Failure<*>)
        onResult(this)
    return this
}

fun <T> ApiResponse<T>.onException(onResult: ApiResponse.Exception<*>. () -> Unit): ApiResponse {
    if(this is ApiResponse.Exception<*>)
        onResult(this)
    return this
}

