package com.fitness.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fitness.R
import com.fitness.screens.workout.session.CustomProgressBar

@Composable
fun TrackingCard(
    name: String,
    icon: ImageVector,
    symbol: String,
    data: Int,
    milestone:Int,
    color:Color
) {
    Column(
        modifier = Modifier
            .padding(start = 12.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(12.dp)

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = "Activity Icon",
                tint = color,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(25.dp)
            )
            Text(
                text = name,
                color = color,
                style = MaterialTheme.typography.titleMedium,
            )
        }
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = data.toString(),
                color = Color.Black,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(end = 8.dp),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = symbol,
                color = Color.Gray,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 4.dp),

            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,

        ){
            Icon(
                imageVector = Icons.Default.EmojiEvents,
                contentDescription = "Activity Icon",
                tint = colorResource(id = R.color.orange),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(25.dp)
            )
            Text(
                text = "Next milestone: $milestone days",
                color = Color.Gray,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 4.dp),
            )
        }

        val progress = if (milestone != 0) data.toFloat() / milestone.toFloat() else 0f

        CustomProgressBar(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .padding(top = 12.dp),
            color = color,
            trackColor = Color.Gray
        )
    }
}

@Preview
@Composable
fun PreviewTrackingCard() {
    TrackingCard(
        name = "Daily Streak",
        icon = Icons.Default.LocalFireDepartment,
        symbol = "days",
        data = 5,
        milestone = 10,
        color = Color(0xFF6200EE)

    )
}