package com.tepe.domain.poko

import androidx.annotation.Keep

@Keep
data class LocationResponse(
    val created: String? = null,
    val dimension: String? = null,
    val id: Int? = null,
    val name: String? = null,
    val residents: List<String?>? = null,
    val type: String? = null,
    val url: String? = null
)