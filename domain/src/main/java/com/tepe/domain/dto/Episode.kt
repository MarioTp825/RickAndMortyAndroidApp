package com.tepe.domain.dto

import java.time.LocalDate

data class Episode(
    val id: Int,
    val name: String,
    val created: LocalDate,
    val airDate: LocalDate
)