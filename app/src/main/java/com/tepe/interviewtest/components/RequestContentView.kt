package com.tepe.interviewtest.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tepe.domain.dto.network.WebRequestState
import com.tepe.domain.dto.network.WebRequestState.*
import com.tepe.interviewtest.R

@Composable
fun RequestContentView(state: MutableState<WebRequestState>, content: @Composable () -> Unit) {
    when (val st = state.value) {
        Loading -> LoadingContent()
        Blank -> LoadingContent()
        is Failed -> Error(message = st.error)
        Successful, Fetching -> content()
    }
}

@Composable
private fun Error(message: String?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val errorTitle = stringResource(R.string.error_title)
        val error = message ?: stringResource(R.string.content_error_label)
        val errorImage = painterResource(R.drawable.rick_error)

        Row {
            Icon(
                Icons.Outlined.Warning,
                contentDescription = error,
                tint = Color.Red,
                modifier = Modifier.padding(end = 10.dp)
            )

            Text(errorTitle, style = MaterialTheme.typography.titleLarge)
        }

        Image(
            errorImage,
            contentDescription = error,
            modifier = Modifier.size(500.dp),
            contentScale = ContentScale.Fit
        )
        Text(error, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
    }
}

@Composable
private fun LoadingContent() {
    val label = stringResource(R.string.loading_label)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LoadingView()
        Text(label, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
    }
}

@Composable
@Preview(showBackground = true, name = "Loading")
fun RequestContentLoadingPreview() {
    val state = remember { mutableStateOf<WebRequestState>(Loading) }
    RequestContentView(state = state) {
        Text("Loading")
    }
}

@Composable
@Preview(showBackground = true, name = "Error")
fun RequestContentErrorPreview() {
    val state =
        remember { mutableStateOf<WebRequestState>(Failed(error = """No more terrible disaster could befall your people than for them to fall into the hands of a Hero.""")) }
    RequestContentView(state = state) {
        Text("Loading")
    }
}