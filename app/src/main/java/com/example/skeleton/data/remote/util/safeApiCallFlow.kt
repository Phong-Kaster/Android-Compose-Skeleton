package com.example.skeleton.data.remote.util

import com.example.skeleton.common.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

fun <T> safeApiCallFlow(
    apiCall: suspend () -> T
): Flow<Resource<T>> = flow {
    emit(Resource.Loading)

    try {
        emit(Resource.Success(apiCall()))
    } catch (e: Exception) {
        emit(Resource.Error(e.message ?: "Unknown error", e))
    }
}.flowOn(Dispatchers.IO)
