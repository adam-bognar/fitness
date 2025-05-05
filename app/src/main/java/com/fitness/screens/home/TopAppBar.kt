package com.fitness.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fitness.R

@Composable
fun TopAppBar(
    alignment: Alignment.Horizontal,
    onBack: (() -> Unit)? = null
) {
    val gradient = Brush.linearGradient(
        colors = listOf(
            colorResource(id = R.color.purple),
            colorResource(id = R.color.pink)
        )
    )

    Row(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .fillMaxWidth()
            .background(colorResource(id = R.color.background))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = when (alignment) {
            Alignment.Start -> Arrangement.Start
            Alignment.CenterHorizontally -> Arrangement.SpaceBetween
            else -> Arrangement.Start
        },

    ) {
        if (alignment == Alignment.CenterHorizontally && onBack != null) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
        }

        Text(
            text = "FitTrack",
            style = TextStyle(
                brush = gradient,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize
            ),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(36.dp))

    }
}

@Preview
@Composable
fun TopAppBarPreview() {
    Column {
        TopAppBar(alignment = Alignment.Start)
        Spacer(modifier = Modifier.height(16.dp))
        TopAppBar(alignment = Alignment.CenterHorizontally, onBack = { /* Handle back */ })
    }
}
