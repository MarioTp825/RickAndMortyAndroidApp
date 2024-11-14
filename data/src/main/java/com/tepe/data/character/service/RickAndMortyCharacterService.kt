package com.tepe.data.character.service

import com.tepe.domain.poko.CharacterPageResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface RickAndMortyCharacterService {

    @GET("character")
    suspend fun getAllCharacters(
        @Query("page") page: Int
    ): CharacterPageResponse

    @GET
    suspend fun getAllCharacters(
        @Url url: String
    ): CharacterPageResponse


    @GET("character/{id}")
    suspend fun getCharacter(
        @Path("id") id: Int
    ): CharacterPageResponse.Character

}