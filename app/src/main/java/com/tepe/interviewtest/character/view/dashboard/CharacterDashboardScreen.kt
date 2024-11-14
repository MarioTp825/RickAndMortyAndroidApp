package com.tepe.interviewtest.character.view.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tepe.domain.dto.Character
import com.tepe.interviewtest.character.viewModel.CharacterDashboardViewModel
import com.tepe.interviewtest.components.LoadingCall
import com.tepe.interviewtest.components.LoadingView
import com.tepe.interviewtest.components.RequestContentView

@Composable
fun CharacterDashboardScreen(
    viewModel: CharacterDashboardViewModel = hiltViewModel(),
    toCharacterDetail: (Int) -> Unit,
) {
    RequestContentView(
        state = viewModel.state
    ) {
        CharacterDashboardView(
            characterList = viewModel.characters.value,
            nextPageUrl = viewModel.nextPageUrl.value,
            toCharacterDetail = toCharacterDetail
        ) {
            viewModel.getCharacters()
        }
    }
}

@Composable
fun CharacterDashboardView(
    characterList: List<Character>,
    nextPageUrl: String? = null,
    toCharacterDetail: (Int) -> Unit,
    fetchData: LoadingCall,
) {
    LazyColumn(
        state = rememberLazyListState(),
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        items(characterList) { character ->
            CharacterCard(character = character, onTap = toCharacterDetail)
        }

        item {
            nextPageUrl?.let {
                LoadingView(url = it, call = fetchData)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CharacterDashboardScreenPreview() {
    CharacterDashboardView(
        characterList = (1..5).map {
            Character(
                id = it,
                name = "Name $it"
            )
        },
        toCharacterDetail = {}
    ) {}
}