package com.tepe.interviewtest.character.view.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tepe.domain.dto.CharacterDetail
import com.tepe.interviewtest.R
import com.tepe.interviewtest.utils.na
import com.tepe.interviewtest.utils.toDateString
import java.time.LocalDate

@Composable
fun CharacterPersonalInfoView(character: CharacterDetail) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 5.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        CharacterProperty(
            label = stringResource(R.string.character_label_name),
            value = character.name
        )
        HorizontalDivider()
        CharacterProperty(
            label = stringResource(R.string.character_label_species),
            value = character.species
        )
        HorizontalDivider()
        CharacterProperty(
            label = stringResource(R.string.character_label_gender),
            value = character.gender
        )
        HorizontalDivider()
        CharacterProperty(
            label = stringResource(R.string.character_label_status),
            value = stringResource(
                if (character.isAlive) R.string.character_text_status_alive
                else R.string.character_text_status_disease
            )
        )
        HorizontalDivider()
        CharacterProperty(
            label = stringResource(R.string.character_label_location),
            value = character.location
        )
        HorizontalDivider()
        CharacterProperty(
            label = stringResource(R.string.character_label_origin),
            value = character.origin
        )
        HorizontalDivider()
        CharacterProperty(
            label = stringResource(R.string.character_label_created),
            value = character.created.toDateString().na()
        )

    }
}

@Composable
fun CharacterProperty(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.titleSmall)
        Text(value, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
@Preview(showBackground = true)
fun CharacterPersonalInfoPreview() {
    CharacterPersonalInfoView(
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
        )
    )
}