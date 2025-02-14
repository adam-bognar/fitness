package com.fitness.screens.workout.session

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitness.R
import kotlinx.coroutines.delay

@Composable
fun Timer(
    initialTime: Int,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var time by remember { mutableIntStateOf(initialTime) }
    var progress by remember { mutableFloatStateOf(1f) }
    var maxTime by remember { mutableIntStateOf(initialTime) }

    LaunchedEffect(time, maxTime) {
        progress = time / maxTime.toFloat()
    }

    LaunchedEffect(time) {
        while (time > 0) {
            delay(1000L)
            time--
        }
        onDismiss()
    }

    val minutes = time / 60
    val seconds = time % 60
    val timeFormatted = String.format("%02d:%02d", minutes, seconds)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.DarkGray)
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(onClick = {
                time = (time - 5).coerceAtLeast(0)

            }) {
                Text(
                    text = "-5s",
                    style = TextStyle(fontSize = 15.sp, color = Color.White)
                )
            }
            Text(
                text = timeFormatted,
                style = TextStyle(fontSize = 20.sp, color = Color.White),
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            TextButton(onClick = {
                time = (time + 5).coerceAtMost(initialTime * 2)
                maxTime+=5
            }) {
                Text(
                    text = "+5s",
                    style = TextStyle(fontSize = 15.sp, color = Color.White)
                )
            }
            TextButton(onClick = onDismiss) {
                Text(
                    text = "Skip",
                    style = TextStyle(fontSize = 15.sp, color = Color.White)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))


        CustomProgressBar(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp)),
            color = colorResource(id = R.color.green),
            trackColor = Color.Black
        )

    }

}

@Composable
fun CustomProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = Color.Green,
    trackColor: Color = Color.Gray,
    height: Dp = 8.dp,
    cornerRadius: Dp = 4.dp
) {
    Canvas(modifier = modifier.height(height)) {
        drawRoundRect(
            color = trackColor,
            size = size.copy(height = size.height),
            cornerRadius = CornerRadius(cornerRadius.toPx())
        )

        // Draw the progress
        drawRoundRect(
            color = color,
            size = size.copy(width = size.width * progress, height = size.height),
            cornerRadius = CornerRadius(cornerRadius.toPx())
        )
    }
}

@Preview
@Composable
fun TimerPreview() {
    Timer(
        initialTime = 60,
        onDismiss = {}
    )
}
