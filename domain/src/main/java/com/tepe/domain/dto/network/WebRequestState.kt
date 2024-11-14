package com.tepe.domain.dto.network

sealed class WebRequestState {
    data object Blank : WebRequestState()

    data object Loading : WebRequestState()

    data object Fetching : WebRequestState()

    class Failed(val error: String?): WebRequestState()

    data object Successful : WebRequestState()
}