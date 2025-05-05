
package com.fitness.screens.workout.session

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.fitness.model.gym.Exercise
import com.fitness.model.gym.ExerciseLog
import com.fitness.model.gym.RepsToWeight
import com.fitness.pose.ExerciseType
@Composable
fun WorkoutItem(
    exercise: Exercise,
    onUpdate: (Exercise) -> Unit = {},
    onDoneClick: () -> Unit = {},
    onNavigate: (Pair<String, Int>) -> Unit = {},
    cameraResult: RepsResult? = null
) {
    var logs by remember(exercise) {
        mutableStateOf(
            exercise.lastLog?.sets?.toMutableList() ?: MutableList(3) { RepsToWeight() }
        )
    }

    val poseDetectionSupported = isPoseDetectionSupported(exercise.name)

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
                color = Color.Black
            ),
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 18.dp)
        ) {
            if (poseDetectionSupported) {
                Spacer(modifier = Modifier.weight(0.5f))
            }
            Text("SET", Modifier.weight(1f), color = Color.Black, textAlign = TextAlign.Center)
            Text("PREVIOUS", Modifier.weight(1f), textAlign = TextAlign.Center)
            Text("KG", Modifier.weight(1f), textAlign = TextAlign.Center)
            Text("REPS", Modifier.weight(1f), textAlign = TextAlign.Center)
            Text("DONE", Modifier.weight(1f), textAlign = TextAlign.Center)
        }

        logs.forEachIndexed { index, log ->
            val setNumber = index + 1
            DataRow(
                setNumber = setNumber,
                previous = log,
                onTick = { kg, reps, isChecked ->
                    if (isChecked) {
                        val kgValue = kg.toIntOrNull() ?: log.weight
                        val repsValue = reps.toIntOrNull() ?: log.reps

                        logs[index] = RepsToWeight(reps = repsValue, weight = kgValue)
                        Log.d("WorkoutItem", "Updated set $index: ${logs[index]}")

                        onUpdate(exercise.copy(lastLog = ExerciseLog(id = 0, sets = logs)))
                        onDoneClick()
                    }
                },
                supported = poseDetectionSupported,
                onCameraClick = { onNavigate(Pair(exercise.name, setNumber)) },
                cameraResult = cameraResult?.takeIf { it.setNumber == setNumber }
            )
        }

        TextButton(
            onClick = {
                logs.add(RepsToWeight())
                onUpdate(exercise.copy(lastLog = ExerciseLog(id = 0, sets = logs)))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Set", color = colorResource(id = R.color.light_blue))
        }
    }
}



fun isPoseDetectionSupported(exerciseName: String): Boolean {
    return ExerciseType.entries.any { it.label.equals(exerciseName, ignoreCase = true) }
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
