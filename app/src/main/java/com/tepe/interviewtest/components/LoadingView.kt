package com.tepe.interviewtest.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.tepe.interviewtest.R

typealias LoadingCall = () -> Unit

@Composable
fun LoadingView(url: String? = null, call: LoadingCall? = null) {
    val context = LocalContext.current
    val desc = stringResource(R.string.loading_desc)

    LaunchedEffect(url) {
        call?.invoke()
    }

    Row(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(context)
                    .data(R.drawable.morty_running)
                    .build()
            ),
            contentDescription = desc,
            modifier = Modifier.size(100.dp)
        )
    }

}

@Composable
@Preview(showBackground = true)
fun LoadingViewPreview() {
    LoadingView()
}