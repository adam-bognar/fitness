package com.fitness.screens.workout.exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ExerciseCard(
    name: String,
    onDelete: () -> Unit
) {
    Row(
      modifier = Modifier
          .fillMaxWidth()
            .padding(top = 12.dp)
          .clip(RoundedCornerShape(16.dp))
          .background(Color.White)
          .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,

        )
        IconButton(
            onClick = onDelete
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Delete",
                tint = Color.Red,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Preview
@Composable
fun ExerciseCardPreview() {
    ExerciseCard(
        name = "Bench Press",
        onDelete = {}
    )
}