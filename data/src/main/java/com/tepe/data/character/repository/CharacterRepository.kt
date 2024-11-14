package com.tepe.data.character.repository

import com.tepe.data.character.service.RickAndMortyCharacterService
import com.tepe.domain.poko.CharacterPageResponse
import javax.inject.Inject

interface CharacterRepository {

    suspend fun getCharactersFirstPage(): CharacterPageResponse

    suspend fun getCharacters(url: String): CharacterPageResponse

    suspend fun getCharacter(id: Int): CharacterPageResponse.Character
}

class CharacterRepositoryImpl @Inject constructor(private val api: RickAndMortyCharacterService): CharacterRepository {

    override suspend fun getCharactersFirstPage(): CharacterPageResponse {
        return api.getAllCharacters(page = 1)
    }

    override suspend fun getCharacters(url: String): CharacterPageResponse {
        return api.getAllCharacters(url = url)
    }

    override suspend fun getCharacter(id: Int): CharacterPageResponse.Character {
        return api.getCharacter(id)
    }
}