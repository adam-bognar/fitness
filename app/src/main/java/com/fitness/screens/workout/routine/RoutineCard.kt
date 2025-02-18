package com.fitness.screens.workout.routine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fitness.model.gym.Exercise
import com.fitness.model.gym.Routine

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RoutineCard(
    routine: Routine,
    onStart: () -> Unit,
    onEdit: (Int) -> Unit,
    onDelete: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Routine Name
            Text(
                text = routine.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )

            // Icons Row
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Routine",
                        tint = Color.Red
                    )
                }
                IconButton(onClick = { onEdit(routine.id) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Routine",
                        tint = Color.Yellow
                    )
                }

                IconButton(onClick = onStart) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Start Routine",
                        tint = Color.Blue
                    )
                }


            }
        }

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
        ) {
            routine.exercises.forEach { exercise ->
                Text(
                    text = exercise.name,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(4.dp),
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview
@Composable
fun RoutineCardPreview() {
    RoutineCard(
        routine = Routine(
            name = "Routine 1",
            description = "Description 1",
            exercises = listOf(
                Exercise(name = "Bench press 1", id = 1),
                Exercise(name = "Push ups", id = 2),
                Exercise(name = "Squat", id = 3),
                Exercise(name = "Shoulder press", id = 2),
                Exercise(name = "Barbell curls", id = 3),
                Exercise(name = "Dumbbell rows", id = 2),
                Exercise(name = "Lat pull downs", id = 3),
            ),
            id = 1
        ),
        onStart = {},
        onEdit = {},
        onDelete = {},
    )
}
