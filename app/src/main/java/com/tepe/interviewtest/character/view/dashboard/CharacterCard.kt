package com.tepe.interviewtest.character.view.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.tepe.domain.dto.Character
import com.tepe.interviewtest.R
import com.tepe.interviewtest.utils.OnTap

@Composable
fun CharacterCard(character: Character, onTap: OnTap = {}) {
    Card(
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 6.dp)
            .clickable(onClick = { onTap(character.id) })
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CharacterImage(
                url = character.thumbnail,
                desc = character.name,
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(30.dp))
            )
            Text(
                character.name,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}

@Composable
fun CharacterImage(
    url: String?,
    desc: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .error(R.drawable.image_error)
            .placeholder(R.mipmap.ic_rick_and_morty_foreground)
            .build(),
        contentDescription = "$desc picture.",
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center,
        modifier = modifier
    )
}

@Composable
@Preview(showBackground = true, name = "Image display")
fun CharacterCardPreview() {
    CharacterCard(
        character = Character(
            id = 1,
            name = "Arthur Weasley",
            thumbnail = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
        )
    )
}

@Composable
@Preview(showBackground = true, name = "Null Image")
fun CharacterCardNoImagePreview() {
    CharacterCard(
        character = Character(
            id = 1,
            name = "Arthur Weasley",
        )
    )
}