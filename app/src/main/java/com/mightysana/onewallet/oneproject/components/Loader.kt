package com.mightysana.onewallet.oneproject.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mightysana.onewallet.R
import com.mightysana.onewallet.oneproject.model.OneAppState

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OneScreen(
    state: OneAppState,
    modifier: Modifier = Modifier,
//    backgroundColor: Color = Color.Black.copy(alpha = 0.3f),
//    backgroundColor: Color = MaterialTheme.colorScheme.background,
    backgroundColor: Color = Color.Transparent,
//    @DrawableRes drawableRes: Int = R.drawable.loading_icon,
    @DrawableRes drawableRes: Int = R.drawable.one_icon_round,
    screenContent: @Composable () -> Unit
) {
    screenContent()
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    AnimatedVisibility(
        visible = state == OneAppState.LOADING,
        enter = scaleIn(),
        exit = scaleOut()
    ) {
        Box(
            modifier = modifier.fillMaxSize().background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = drawableRes), // Ganti dengan ikon loading Anda
                contentDescription = "Loading",
                modifier = Modifier
                    .size(64.dp)
                    .scale(scale)
                    .rotate(rotation)
            )
        }
    }
}