
package com.fitness.screens.workout.session

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fitness.R

@Composable
fun WorkoutHeader(
    name: String,
    onFinish: () -> Unit,
    onTimerClick: () -> Unit,
    onBack: () -> Unit,
) {
    Box(
        modifier = Modifier
        .fillMaxWidth()
        .background(colorResource(R.color.background))
        .padding(top = 10.dp, bottom = 10.dp)


    )  {
        Row(modifier = Modifier.align(Alignment.CenterStart)) {
            IconButton(
                modifier = Modifier.align(Alignment.CenterVertically),
                onClick = { onBack() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back button",
                    tint = colorResource(id = R.color.button_color)
                )
            }
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = name,
                color = colorResource(id = R.color.text_color),
                style = MaterialTheme.typography.titleLarge
            )
        }

        Row (
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 10.dp)
        ){
            IconButton(
                modifier = Modifier.align(Alignment.CenterVertically),
                onClick = onTimerClick
            ) {
                Icon(
                    imageVector = Icons.Default.Timer,
                    contentDescription = "Timer",
                    tint = colorResource(id = R.color.button_color)
                )
            }
            Button(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .wrapContentSize(),
                onClick = onFinish,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.button_color))
            ) {
                Text("Finish",
                    color = colorResource(id = R.color.button_text_color),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun WorkoutHeaderPreview() {
    WorkoutHeader(
        name = "Workout",
        onFinish = { },
        onTimerClick = { },
        onBack = { }
    )
}
