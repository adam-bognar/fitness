package com.fitness.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fitness.R

@Composable
fun TopAppBar() {
    val gradient = Brush.linearGradient(
        colors = listOf(
            colorResource(id = R.color.purple),
            colorResource(id = R.color.pink)
        )
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.background))
            .padding(16.dp)


    ) {
        Text(
            text = "FitTrack",
            style = TextStyle(
                brush = gradient,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize
            ),
            fontWeight = FontWeight.Bold
        )

    }
}

@Preview
@Composable
fun TopAppBarPreview() {
    TopAppBar()
}