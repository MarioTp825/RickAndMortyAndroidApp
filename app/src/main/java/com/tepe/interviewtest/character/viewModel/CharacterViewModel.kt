package com.tepe.interviewtest.character.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tepe.data.character.repository.CharacterRepository
import com.tepe.data.episode.repository.EpisodeRepository
import com.tepe.domain.dto.CharacterDetail
import com.tepe.domain.dto.Episode
import com.tepe.domain.dto.network.WebRequestState
import com.tepe.interviewtest.utils.callService
import com.tepe.interviewtest.utils.na
import com.tepe.interviewtest.utils.toDate
import com.tepe.interviewtest.utils.toLocaleEpisode
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = CharacterViewModel.CharacterViewModelFactory::class)
class CharacterViewModel @AssistedInject constructor(
    @Assisted private val id: Int,
    private val repository: CharacterRepository,
    private val episodeRepository: EpisodeRepository
) : ViewModel() {
    val state = mutableStateOf<WebRequestState>(WebRequestState.Blank)
    val episodesState = mutableStateOf<WebRequestState>(WebRequestState.Blank)

    val detail = mutableStateOf<CharacterDetail?>(null)
    val episodes = mutableStateOf<List<Episode>>(listOf())

    init {
        getCharacterData()
    }

    private fun getCharacterData() = viewModelScope.launch (Dispatchers.IO) {
        val response = callService(state) {
            repository.getCharacter(id = id)
        }

        val data = response ?: return@launch

        detail.value = CharacterDetail(
            id = data.id ?: -1,
            name = data.name.na(),
            thumbnail = data.image,
            episodesUrl = data.episode?.filterNotNull().orEmpty(),
            origin = data.origin?.name?.na().na(),
            location = data.location?.name?.na().na(),
            isAlive = data.status == "Alive",
            species = data.species.na(),
            gender = data.gender.na(),
            created = data.created.toDate(),
        )
    }

    fun getAllEpisodes() = viewModelScope.launch {
        if (episodesState.value != WebRequestState.Blank) {
            return@launch
        }
        val episodesUrl = detail.value?.episodesUrl ?: return@launch
        episodesState.value = WebRequestState.Loading

        val requests = episodesUrl.map { url ->
            async(Dispatchers.IO) {
                callService(state = episodesState) {
                    episodeRepository.getEpisode(url = url)
                }?.toLocaleEpisode()
            }
        }
        episodes.value = requests.awaitAll().filterNotNull()
    }

    @AssistedFactory
    interface CharacterViewModelFactory {
        fun create(id: Int): CharacterViewModel
    }

}

