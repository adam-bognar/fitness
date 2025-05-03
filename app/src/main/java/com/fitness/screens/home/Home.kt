package com.fitness.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fitness.R
import com.fitness.components.ActivityCard
import com.fitness.components.TrackingCard
import com.fitness.data.streak.StreakInfo
import com.fitness.data.streak.StreakViewModel

@Composable
fun Home(
    onActivityClick: (String) -> Unit,
    onNavigate: (String) -> Unit,
    streakViewModel: StreakViewModel = hiltViewModel()
) {

    val dailyStreakData by streakViewModel.dailyStreak.collectAsState()
    val weeklyStreakData by streakViewModel.weeklyStreak.collectAsState()

    val activities = listOf(
        Triple(
            "Workout",
            painterResource(id = R.drawable.exercise),
            listOf(colorResource(R.color.light_blue), colorResource(R.color.blue))),
        Triple("Running",
            painterResource(id = R.drawable.sprint),
            listOf(colorResource(R.color.light_green), colorResource(R.color.green))),
    )

    val streaks = listOf(
        StreakInfo(
            name = "Daily Streak",
            icon = Icons.Default.LocalFireDepartment,
            color = colorResource(R.color.orange),
            currentStreak = dailyStreakData.currentStreak,
            milestone = listOf(7, 30, 100, 365)
        ),
        StreakInfo(
            name = "Weekly Streak",
            icon = Icons.Default.CalendarToday,
            color = colorResource(R.color.blue),
            currentStreak = weeklyStreakData.currentStreak,
            milestone = listOf(4, 12, 26, 52)
        )
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
                    items(streaks.size) { index ->
                        val streakInfo = streaks[index]
                        val nextMilestone = streakInfo.milestone.firstOrNull { it > streakInfo.currentStreak }
                            ?: streakInfo.milestone.lastOrNull()
                            ?: 10
                        TrackingCard(
                            name = streakInfo.name,
                            icon = streakInfo.icon,
                            symbol = if (streakInfo.name == "Daily Streak") "days" else "weeks",
                            data = streakInfo.currentStreak,
                            milestone = nextMilestone,
                            color = streakInfo.color,
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

//                Button(
//                    onClick = { onNavigate("CAMERA") },
//                    modifier = Modifier
//                        .padding(16.dp)
//                ) {
//                    Text("open camera")
//                }


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