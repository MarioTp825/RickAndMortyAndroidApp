package com.tepe.interviewtest.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize


fun Modifier.skeletonEffect(): Modifier = composed {
    //We will store the size of the view in here after the screen has been rendered
    var size by remember { mutableStateOf(IntSize.Zero) }

    //This will help us obtain the starting position of the gradient within the view
    val transition = rememberInfiniteTransition(label = "")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(animation = tween(1000)),
        label = ""
    )

    //We change the gradient's position altering between dark and light green to obtain
    //the portal gun effect
    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFF00FF00),
                Color(0xFF0B660B),
                Color(0xFF00FF00),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width, size.height.toFloat())
        )
    ).onGloballyPositioned {
        size = it.size
    }
}