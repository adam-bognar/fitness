package com.fitness.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fitness.R
import com.fitness.components.ActivityCard
import com.fitness.components.TrackingCard

@Composable
fun Home(
    onActivityClick: (String) -> Unit,
    onNavigate: (String) -> Unit
) {
    val activities = listOf(
        Triple(
            "Workout",
            painterResource(id = R.drawable.exercise),
            listOf(colorResource(R.color.light_blue), colorResource(R.color.blue))),
        Triple("Running",
            painterResource(id = R.drawable.sprint),
            listOf(colorResource(R.color.light_green), colorResource(R.color.green))),
    )

    Scaffold(
        topBar = { TopAppBar(
            alignment = Alignment.Start
        ) },
        bottomBar = {
            BottomBar(
            onClick = onNavigate
        ) },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(colorResource(id = R.color.background))
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(top = 16.dp).padding(end = 12.dp)

                ) {
                    items(2){index ->
                        TrackingCard(   // TrackingCard is a custom composable
                            name = "Calories Burned",
                            icon = painterResource(id = R.drawable.exercise),
                            symbol = "min",
                            data = 30
                        )
                    }
                }

                Text(
                    text = "Choose Your Activity",
                    modifier = Modifier
                        .padding(top = 20.dp,start = 16.dp),
                    color = colorResource(id = R.color.text_color),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                ) {
                    items(activities.size) { index ->
                        val (name, icon, gradientColors) = activities[index] // Destructure Triple

                        ActivityCard(
                            name = name,
                            icon = icon,
                            gradientColors = gradientColors,
                            onStart = onActivityClick
                        )
                    }
                }
            }
        }
    )
}




@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Home(
        onActivityClick = {},
        onNavigate = {}
    )
}