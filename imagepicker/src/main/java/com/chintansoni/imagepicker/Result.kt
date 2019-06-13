package com.chintansoni.imagepicker

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Failure<T>(val throwable: Throwable) : Result<T>()
}