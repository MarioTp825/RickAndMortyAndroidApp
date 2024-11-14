package com.tepe.interviewtest.utils

import androidx.compose.runtime.MutableState
import com.tepe.domain.dto.network.WebRequestState

suspend inline fun <T> callService(state: MutableState<WebRequestState>, crossinline call: suspend () -> T): T? {
    try {
        call()?.let { response ->
            state.value = WebRequestState.Successful
            return response
        }
        throw NullPointerException("Response was null")
    } catch (ex: NullPointerException) {
        state.value = WebRequestState.Failed(ex.message ?: "The API returned a null value")
    } catch (ex: Exception) {
        state.value = WebRequestState.Failed(ex.message ?: "Unknown Error while calling API")
    }
    return null
}