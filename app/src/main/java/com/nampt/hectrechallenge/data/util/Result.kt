package com.nampt.hectrechallenge.data.util

import android.util.Log
import com.nampt.hectrechallenge.domain.model.RateVolumeJson
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.isActive


sealed class DataResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : DataResult<T>()
    data class Failure(val exception: ErrorResponse? = null) : DataResult<Nothing>()
    object Loading : DataResult<Nothing>()
}

data class ErrorResponse(val message: String?)

fun <T> ProducerScope<T>.defaultOffer(t: T) {
    try {
        if (isActive && !isClosedForSend) {
            trySend(t)
        }
    } catch (e: java.lang.Exception) {
        Log.e("nampt", "defaultOffer:  whoops")
    }
}

