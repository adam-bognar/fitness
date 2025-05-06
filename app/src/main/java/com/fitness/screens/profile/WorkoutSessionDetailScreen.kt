package com.fitness.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitness.R
import com.fitness.model.gym.Exercise
import com.fitness.model.gym.RepsToWeight
import com.fitness.model.gym.Session
import com.fitness.screens.home.TopAppBar

@Composable
fun WorkoutSessionDetailScreen(
    session: Session,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                alignment = Alignment.CenterHorizontally,
                onBack = onNavigateBack,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(colorResource(R.color.background))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = session.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                session.date?.let {
                    Text(
                        text = formatFirebaseTimestamp(it),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Gray
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(session.log.size) { index ->
                    val exercise = session.log[index]
                    ExerciseLogItemView(exercise = exercise)
                }
            }
        }
    }
}

@Composable
fun ExerciseLogItemView(exercise: Exercise) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(vertical = 10.dp)
    ) {
        Text(
            text = exercise.name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 18.dp, start = 16.dp, end = 16.dp)
        ) {
            Text("SET", Modifier.weight(1f), color = Color.Gray, textAlign = TextAlign.Center)
            Text("KG", Modifier.weight(1f), color = Color.Gray, textAlign = TextAlign.Center)
            Text("REPS", Modifier.weight(1f), color = Color.Gray, textAlign = TextAlign.Center)
        }

        exercise.lastLog?.sets?.forEachIndexed { index, set ->
            ExerciseSetView(setNumber = index + 1, repsToWeight = set)
        }
    }
}

@Composable
fun ExerciseSetView(setNumber: Int, repsToWeight: RepsToWeight) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = setNumber.toString(),
            modifier = Modifier
                .weight(1f),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
        Text(
            text = repsToWeight.weight.toString(),
            modifier = Modifier
                .weight(1f),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
        Text(
            text = repsToWeight.reps.toString(),
            modifier = Modifier
                .weight(1f),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
    }
}


