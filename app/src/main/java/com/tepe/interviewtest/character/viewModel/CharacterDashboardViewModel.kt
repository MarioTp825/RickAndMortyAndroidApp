package com.tepe.interviewtest.character.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tepe.data.character.repository.CharacterRepository
import com.tepe.domain.dto.Character
import com.tepe.domain.dto.network.WebRequestState
import com.tepe.interviewtest.utils.callService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDashboardViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    val characters = mutableStateOf<List<Character>>(listOf())
    val state = mutableStateOf<WebRequestState>(WebRequestState.Blank)

    var nextPageUrl = mutableStateOf<String?>(null)

    init {
        getCharactersFirstPage()
    }


    private fun getCharactersFirstPage() = viewModelScope.launch(Dispatchers.IO) {
        state.value = WebRequestState.Loading
        val response = callService(
            state = state
        ) {
            repository.getCharactersFirstPage()
        }

        val data = response?.results ?: return@launch

        nextPageUrl.value = response.info?.next
        characters.value = data.filterNotNull().map { character ->
            Character(
                id = character.id ?: -1,
                name = character.name ?: "N/A",
                thumbnail = character.image
            )
        }.filter { it.id != -1 }
    }

    fun getCharacters() = viewModelScope.launch(Dispatchers.IO) {
        val pageUrl = nextPageUrl.value ?: return@launch

        state.value = WebRequestState.Fetching
        val response = callService(
            state = state
        ) {
            repository.getCharacters(pageUrl)
        }

        val data = response?.results ?: return@launch

        nextPageUrl.value = response.info?.next
        characters.value += data.filterNotNull().map { character ->
            Character(
                id = character.id ?: -1,
                name = character.name ?: "N/A",
                thumbnail = character.image
            )
        }.filter { it.id != -1 }
    }
}