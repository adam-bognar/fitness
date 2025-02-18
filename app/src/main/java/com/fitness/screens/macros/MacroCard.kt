package com.fitness.screens.macros

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fitness.screens.workout.session.CustomProgressBar

@Composable
fun MacroCard(
    title: String,
    value: Float,
    target: Int,
    unit: String
) {
    val progress = (value / target.toFloat()).coerceIn(0f, 1f)

    Box(
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .padding(12.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = if (value % 1 == 0f) "${value.toInt()}" else "%.1f".format(value).replace(",", "."),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "/ $target $unit",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            CustomProgressBar(
                progress = progress,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

}

@Preview
@Composable
fun MacroCardPreview() {
    MacroCard(
        title = "Protein",
        value = 100.5f,
        target = 150,
        unit = "g"
    )
}