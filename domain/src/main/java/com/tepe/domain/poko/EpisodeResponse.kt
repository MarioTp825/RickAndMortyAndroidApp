package com.tepe.domain.poko

import androidx.annotation.Keep

@Keep
data class EpisodeResponse(
    val airDate: String? = null,
    val characters: List<String?>? = null,
    val created: String? = null,
    val episode: String? = null,
    val id: Int? = null,
    val name: String? = null,
    val url: String? = null
)