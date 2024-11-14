package com.tepe.interviewtest.character.view.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.tepe.domain.dto.CharacterDetail
import com.tepe.domain.dto.Episode
import com.tepe.domain.dto.network.WebRequestState
import com.tepe.interviewtest.R
import com.tepe.interviewtest.character.view.dashboard.CharacterImage
import com.tepe.interviewtest.character.viewModel.CharacterViewModel
import com.tepe.interviewtest.components.RequestContentView
import java.time.LocalDate

@Composable
fun CharacterDetailScreen(
    id: Int,
    navigateBack: () -> Unit,
    viewModel: CharacterViewModel = hiltViewModel<CharacterViewModel, CharacterViewModel.CharacterViewModelFactory>(
        creationCallback = { factory -> factory.create(id) })
) {
    RequestContentView(state = viewModel.state) {
        val character = viewModel.detail.value
        if (character != null) {
            CharacterDetailView(
                character = character,
                episodes = viewModel.episodes.value,
                episodeState = viewModel.episodesState,
                onLoad = {
                    viewModel.getAllEpisodes()
                },
                navigateBack = navigateBack,
                isFavorite = true
            )
        }
    }
}

@Composable
fun CharacterDetailView(
    character: CharacterDetail,
    episodes: List<Episode>,
    episodeState: MutableState<WebRequestState>,
    onLoad: () -> Unit,
    navigateBack: () -> Unit,
    isFavorite: Boolean
) {

    ConstraintLayout(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {
        val (header, liked, content, backButton) = createRefs()
        CharacterImage(
            url = character.thumbnail,
            desc = character.name,
            modifier = Modifier
                .constrainAs(header) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .height(200.dp)
                .fillMaxWidth(),
        )

        CharacterInformation(
            character = character,
            episodes = episodes,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .constrainAs(content) {
                    top.linkTo(header.bottom)
                    bottom.linkTo(backButton.top, margin = 5.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                },
            episodeState = episodeState,
            onLoad = onLoad
        )

        Button(
            onClick = navigateBack,
            modifier = Modifier
                .constrainAs(backButton) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Text("Done")
        }

        FavoriteButton(
            modifier = Modifier
                .constrainAs(liked) {
                    top.linkTo(content.top)
                    bottom.linkTo(content.top)
                    end.linkTo(parent.end)
                },
            isFavorite = isFavorite,
        ) {
            //TODO On Click
        }
    }
}

@Composable
private fun FavoriteButton(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onClick: () -> Unit
) {
    val iconDescription = stringResource(
        if (isFavorite) R.string.favorite_desc
        else R.string.favorite_remove_desc
    )
    val favIcon = if (isFavorite) Icons.Default.Favorite
    else Icons.Default.FavoriteBorder
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = favIcon,
            contentDescription = iconDescription,
            tint = Color.Red,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(all = 8.dp)
                .clip(CircleShape)
                .size(50.dp)
        )
    }
}

@Composable
private fun CharacterInformation(
    modifier: Modifier = Modifier,
    character: CharacterDetail,
    episodes: List<Episode>,
    episodeState: MutableState<WebRequestState>,
    onLoad: () -> Unit
) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    Column(modifier = modifier) {
        TabRow(selectedIndex) {
            Tab(
                selected = selectedIndex == 0,
                onClick = { selectedIndex = 0 },
                modifier = Modifier.padding(vertical = 10.dp)
            ) {
                Text("Info")
            }
            Tab(
                selected = selectedIndex == 1,
                onClick = {
                    selectedIndex = 1
                    onLoad()
                },
                modifier = Modifier.padding(vertical = 10.dp)
            ) {
                Text("Episodes")
            }
        }

        when (selectedIndex) {
            0 -> CharacterPersonalInfoView(character)
            else -> RequestContentView(state = episodeState) {
                EpisodeListView(episodes = episodes)
            }
        }
    }
}

@Composable
@Preview
fun CharacterDetailViewPreview() {
    val state = remember { mutableStateOf<WebRequestState>(WebRequestState.Successful) }
    CharacterDetailView(
        character = CharacterDetail(
            id = 0,
            name = "Bellatrix Lestrange",
            episodesUrl = listOf(),
            gender = "Female",
            origin = "Slytherin",
            location = "Azkaban",
            species = "Human",
            created = LocalDate.now(),
            isAlive = false
        ),
        episodes = listOf(),
        episodeState = state,
        onLoad = {},
        navigateBack = {},
        isFavorite = true
    )
}