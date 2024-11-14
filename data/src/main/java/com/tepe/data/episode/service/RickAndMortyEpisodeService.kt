package com.tepe.data.episode.service

import com.tepe.domain.poko.EpisodeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface RickAndMortyEpisodeService {

    @GET("episode/{id}")
    suspend fun getEpisode(
        @Path("id") id: Int
    ): EpisodeResponse

    @GET
    suspend fun getEpisode(
        @Url url: String
    ): EpisodeResponse
}