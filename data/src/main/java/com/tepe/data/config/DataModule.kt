package com.tepe.data.config

import com.tepe.data.character.repository.CharacterRepository
import com.tepe.data.character.repository.CharacterRepositoryImpl
import com.tepe.data.character.service.RickAndMortyCharacterService
import com.tepe.data.episode.service.RickAndMortyEpisodeService
import com.tepe.data.utils.BaseURL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BaseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideCharacterService(retrofit: Retrofit): RickAndMortyCharacterService {
        return retrofit
            .create(RickAndMortyCharacterService::class.java)
    }

    @Provides
    @Singleton
    fun provideCharacterRepository(service: RickAndMortyCharacterService): CharacterRepository {
        return CharacterRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideEpisodeService(retrofit: Retrofit): RickAndMortyEpisodeService {
        return retrofit
            .create(RickAndMortyEpisodeService::class.java)
    }
}