
package com.fitness.screens.workout.session

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fitness.R
import com.fitness.model.gym.RepsToWeight

@Composable
fun DataRow(
    setNumber: Int,
    previous: RepsToWeight,
    onTick: (String, String, Boolean) -> Unit,
    supported: Boolean,
    onCameraClick: () -> Unit,
    cameraResult: RepsResult? = null
) {

    var repsInput by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(cameraResult) {
        if (cameraResult != null) {
            repsInput = cameraResult.reps.toString()
        }
    }


    var kgInput by rememberSaveable { mutableStateOf("") }
    var isChecked by rememberSaveable { mutableStateOf(false) }

    var previousWeight by rememberSaveable { mutableStateOf(previous.weight.toString()) }
    var previousReps by rememberSaveable { mutableStateOf(previous.reps.toString()) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        if(supported) {
            IconButton(
                onClick = {
                    onCameraClick()
                          Log.d("CameraClick", "Camera clicked at set $setNumber")
                          },
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "Pose Detection",
                    modifier = Modifier
                        .weight(0.5f),
                    tint = colorResource(id = R.color.light_blue)
                )
            }
        }
        Text(
            text = setNumber.toString(),
            modifier = Modifier
                .weight(1f)
                .height(24.dp),
            textAlign = TextAlign.Center,
            color = Color.Black
        )
        Text(
            text = "${previous.weight}kg x ${previous.reps}",
            modifier = Modifier
                .weight(1f)
                .height(24.dp),
            textAlign = TextAlign.Center,
            style = TextStyle(color = Color.Gray),

        )
        PlaceholderTextField(
            value = kgInput,
            onValueChange = { kgInput = it },
            placeholder = previousWeight,
            modifier = Modifier.weight(1f)
        )
        PlaceholderTextField(
            value = repsInput,
            onValueChange = { repsInput = it },
            placeholder = previousReps,
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = {
                isChecked = !isChecked
                onTick(kgInput, repsInput, isChecked)

            },
            modifier = Modifier
                .weight(1f)
                .height(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Done",
                tint = if (isChecked) Color.Green else Color.Black
            )
        }
    }
}
