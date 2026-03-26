package com.example.skeleton.data.remote.util

import com.example.skeleton.common.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Wraps API calls in a Flow that emits [Result] states.
 * Use in repository implementations for consistent error handling.
 * The [Throwable] in Result.Error can be mapped to UI messages via [Throwable.toUiMessage].
 */
fun <T> safeApiCallFlow(
    apiCall: suspend () -> T
): Flow<Result<T>> = flow {
    emit(Result.Loading)

    try {
        emit(Result.Success(apiCall()))
    } catch (e: Exception) {
        emit(Result.Error(e.message ?: "Unknown error", e))
    }
}.flowOn(Dispatchers.IO)
