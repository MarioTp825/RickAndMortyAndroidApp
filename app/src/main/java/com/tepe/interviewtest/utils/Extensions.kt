package com.tepe.interviewtest.utils

import com.tepe.domain.dto.Episode
import com.tepe.domain.poko.EpisodeResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/** For Not available values */
fun String?.na(): String = this ?: "N/A"

fun String?.toDate(): LocalDate {
    this ?: return LocalDate.now()
    return try {
        LocalDate.parse(this)
    } catch (ex: Exception) {
        LocalDate.now()
    }
}

fun LocalDate.toDateString(pattern: String = "dd/MM/yyyy"): String? {
    return try {
        DateTimeFormatter.ofPattern(pattern).format(this)
    } catch (ex: Exception) {
        null
    }
}

fun EpisodeResponse.toLocaleEpisode(): Episode {
    return Episode(
        id = this.id ?: -1,
        name = this.name.na(),
        airDate = this.airDate.toDate(),
        created = this.created.toDate(),
    )
}