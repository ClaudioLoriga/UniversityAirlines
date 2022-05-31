package com.example.universityairlines.model

sealed class ApiLoginResult<out T : Any> {
    data class Success<out T : Any>(val value: T) : ApiLoginResult<T>()
    data class Failure(val errorResponse: ErrorResponse? = null) : ApiLoginResult<Nothing>()
}