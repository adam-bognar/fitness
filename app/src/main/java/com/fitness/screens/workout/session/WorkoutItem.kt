
package com.fitness.screens.workout.session

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitness.R
import com.fitness.model.Exercise
import com.fitness.model.ExerciseLog
import com.fitness.model.RepsToWeight

@Composable
fun WorkoutItem(
    exercise: Exercise,
    onUpdate: (Exercise) -> Unit = {},
    onDoneClick: () -> Unit = {}
) {

    var logs by remember { mutableStateOf(exercise.lastLog?.sets ?: listOf()) }
    val newLogs by remember { mutableStateOf(mutableListOf<RepsToWeight>()) }

    if (logs.isEmpty()) {
        logs = List(3) { RepsToWeight() }
    } else if (logs.size < 3) {
        logs = logs + List(3 - logs.size) { RepsToWeight() }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color.White)
    ) {
        Text(
            text = exercise.name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                color = Color.White
            ),
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 18.dp)
        ) {
            Text(text = "SET", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            Text(text = "PREVIOUS", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            Text(text = "KG", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            Text(text = "REPS", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            Text(text = "DONE", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
        }

        logs.forEachIndexed { index, log ->
            DataRow(
                setNumber = index + 1,
                previous = log,
                onTick = { kg, reps, isChecked ->
                    if (isChecked) {
                        val kgValue = if (kg.isEmpty()) log.weight else kg.toInt()
                        val repsValue = if (reps.isEmpty()) log.reps else reps.toInt()
                        newLogs.add(RepsToWeight(reps = repsValue, weight = kgValue))
                        onDoneClick()
                    } else {
                        newLogs.removeIf { it.reps == log.reps && it.weight == log.weight }
                    }
                    val updatedExercise = exercise.copy(lastLog = ExerciseLog(id = 0,sets = newLogs.toList()))
                    Log.d("WorkoutItem", "Updated exercise: $updatedExercise")
                    onUpdate(updatedExercise)
                },
            )
        }

        TextButton(
            onClick = {
                logs = logs + List(1) { RepsToWeight() }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add Set",
                color = colorResource(id = R.color.light_blue))
        }
    }
}



@Composable
fun PlaceholderTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(fontSize = 16.sp, textAlign = TextAlign.Center)
) {
    Box(
        modifier = modifier.wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        if (value.isEmpty()) {
            Text(
                text = placeholder,
                style = textStyle.copy(color = Color.Gray),
                modifier = Modifier
                    .height(24.dp)
                    .fillMaxWidth(),

                )
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = textStyle,
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutItemPreview() {
    WorkoutItem(
        exercise = Exercise(
            id = 0,
            name = "Exercise 1",
            lastLog = ExerciseLog(
                id = 0,
                sets = listOf(
                    RepsToWeight(reps = 10, weight = 20),
                    RepsToWeight(reps = 12, weight = 25),
                    RepsToWeight(reps = 15, weight = 30),
                )
            )
        )
    )
}
