package com.axelbrians.compose_multiplatform

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.axelbrians.compose_multiplatform.card.ArticleCard
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import compose_multiplatform.composeapp.generated.resources.Res
import compose_multiplatform.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.launch

@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(true) }
        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")

                    Spacer(Modifier.height(12.dp))

                    val interactions = remember { MutableInteractionSource() }
                    val isPressed = interactions.collectIsPressedAsState()

                    val cardScaleFactor = animateFloatAsState(
                        targetValue = if (isPressed.value) 1.05f else 1f,
                        label = "CardScaleFactor",
                    )

                    ArticleCard(
                        imageUrl = "https://your-image-url.com/image.jpg",
                        title = "How I prototype with colors in Jetpack Compose",
                        subtitle = "Don't waste time choosing colors in Jetpack Compose",
                        footnote = "Feb 21, 2025 • 4 min read • 4 comments",
                        onClick = { /* Handle card click */ },
                        modifier = Modifier
                            .graphicsLayer {
                                scaleX = cardScaleFactor.value
                                scaleY = cardScaleFactor.value
                            }
                            .clickable(
                                interactionSource = interactions,
                                indication = null,
                                onClick = { }
                            )
                    )
                }
            }
        }
    }
}