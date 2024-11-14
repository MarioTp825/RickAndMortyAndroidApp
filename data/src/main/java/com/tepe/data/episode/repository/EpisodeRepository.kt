package com.tepe.data.episode.repository

import com.tepe.data.episode.service.RickAndMortyEpisodeService
import com.tepe.domain.poko.EpisodeResponse
import javax.inject.Inject

class EpisodeRepository @Inject constructor(
    private val episodeService: RickAndMortyEpisodeService
) {

    suspend fun getEpisode(id: Int): EpisodeResponse {
        return episodeService.getEpisode(id = id)
    }

    suspend fun getEpisode(url: String): EpisodeResponse {
        return episodeService.getEpisode(url = url)
    }
}