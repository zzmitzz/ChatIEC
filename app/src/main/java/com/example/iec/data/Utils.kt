package com.example.iec.data



sealed interface APIResult<out T> {
    data class Success<out T>(val data: T) : APIResult<T>
    data class Failure(val exception: String) : APIResult<Nothing>
}