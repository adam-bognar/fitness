package com.fitness.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fitness.R

@Composable
fun ActivityCard(
    name: String,
    icon: Painter,
    gradientColors: List<Color>,
    onStart: () -> Unit,
) {
    val gradient = Brush.linearGradient(
        colors = if (gradientColors.size >= 2) gradientColors else listOf(Color.Gray, Color.DarkGray) // Fallback colors
    )

    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(0.5f)
            .clip(RoundedCornerShape(16.dp))
            .background(gradient)
            .padding(16.dp)
            .clickable { onStart() }
    ) {
        Icon(
            painter = icon,
            contentDescription = "Activity Icon",
            tint = Color.White,
            modifier = Modifier.size(30.dp)
        )
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Preview
@Composable
fun ActivityCardPreview() {
    ActivityCard(
        name = "Workout",
        icon = painterResource(id = R.drawable.exercise),
        gradientColors = listOf(Color(0xFF7B1FA2), Color(0xFFE91E63)), // Purple to Pink gradient
        onStart = {}
    )
}
