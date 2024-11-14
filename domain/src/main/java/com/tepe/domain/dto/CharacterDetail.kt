package com.tepe.domain.dto

import java.time.LocalDate

data class CharacterDetail(
    val id: Int,
    val name: String,
    val thumbnail: String? = null,
    val episodesUrl: List<String>,
    val origin: String,
    val location: String,
    val isAlive: Boolean,
    val species: String,
    val gender: String,
    val created: LocalDate
)
