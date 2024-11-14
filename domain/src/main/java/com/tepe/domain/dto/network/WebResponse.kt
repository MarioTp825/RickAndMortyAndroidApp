package com.tepe.domain.dto.network

data class WebResponse<T>(
    val response: T? = null,
    val error: String? = null
)