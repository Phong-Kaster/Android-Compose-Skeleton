package com.example.skeleton.common

sealed class Resource<out T> {

    data object Loading : Resource<Nothing>()

    data class Success<T>(val data: T) : Resource<T>()

    data class Error(val message: String, val throwable: Throwable? = null) : Resource<Nothing>()
}
