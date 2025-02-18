
package com.fitness.screens.workout.session

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fitness.model.gym.RepsToWeight

@Composable
fun DataRow(
    setNumber: Int,
    previous: RepsToWeight,
    onTick: (String, String, Boolean) -> Unit,
) {

    var kgInput by remember { mutableStateOf("") }
    var repsInput by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Text(
            text = setNumber.toString(),
            modifier = Modifier
                .weight(1f)
                .height(24.dp),
            textAlign = TextAlign.Center,
        )
        Text(
            text = "${previous.weight}kg x ${previous.reps}",
            modifier = Modifier
                .weight(1f)
                .height(24.dp),
            textAlign = TextAlign.Center,
            style = TextStyle(color = Color.Gray)

        )
        PlaceholderTextField(
            value = kgInput,
            onValueChange = { kgInput = it },
            placeholder = previous.weight.toString(),
            modifier = Modifier.weight(1f)
        )
        PlaceholderTextField(
            value = repsInput,
            onValueChange = { repsInput = it },
            placeholder = previous.reps.toString(),
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
