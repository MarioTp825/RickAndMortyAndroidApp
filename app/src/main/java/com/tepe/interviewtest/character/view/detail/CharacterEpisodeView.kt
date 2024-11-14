package com.tepe.interviewtest.character.view.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tepe.domain.dto.Episode
import com.tepe.interviewtest.R
import com.tepe.interviewtest.utils.na
import com.tepe.interviewtest.utils.toDateString
import java.time.LocalDate

@Composable
fun EpisodeListView(episodes: List<Episode>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 5.dp)
            .background(MaterialTheme.colorScheme.background),
        state = rememberLazyListState()
    ) {
        items(episodes) { episode ->
            EpisodeItem(
                episode = episode
            )
        }
    }
}

@Composable
private fun EpisodeItem(episode: Episode) {
    val created =
        stringResource(R.string.episode_label_created) + episode.created.toDateString().na()

    val airDate =
        stringResource(R.string.episode_label_air_date) + episode.airDate.toDateString().na()
    Card(modifier = Modifier.padding(vertical = 5.dp)) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Outlined.PlayArrow,
                contentDescription = episode.name,
                modifier = Modifier.size(50.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 10.dp),
            ) {
                Text(
                    episode.name,
                    style = MaterialTheme.typography.titleMedium
                )


                Text(
                    airDate,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    created,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun EpisodeListPreview() {
    EpisodeListView(
        episodes = (1..5).map {
            Episode(
                id = it,
                name = "Episode $it",
                created = LocalDate.now().minusDays(it.toLong()),
                airDate = LocalDate.now().plusDays(it.toLong())
            )
        }
    )
}