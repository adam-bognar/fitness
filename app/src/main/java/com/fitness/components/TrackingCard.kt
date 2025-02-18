package com.fitness.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fitness.R

@Composable
fun TrackingCard(
    name: String,
    icon: Painter,
    symbol: String,
    data: Int
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
                painter = icon,
                contentDescription = "Activity Icon",
                tint = Color.Black,
                modifier = Modifier.padding(end = 8.dp).size(25.dp)
            )
            Text(
                text = name,
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium,
            )
        }
        Row(
            modifier = Modifier.padding(top = 12.dp),
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
    }
}

@Preview
@Composable
fun PreviewTrackingCard() {
    TrackingCard(
        name = "Calories",
        icon = painterResource(id = R.drawable.exercise),
        symbol = "Kcal",
        data = 200
    )
}