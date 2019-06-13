package com.chintansoni.imagepicker

sealed class Result {
    data class Success(val output: Output) : Result()
    data class Failure(val throwable: Throwable) : Result()
}