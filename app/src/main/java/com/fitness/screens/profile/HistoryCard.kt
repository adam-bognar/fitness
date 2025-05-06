package com.fitness.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fitness.R

@Composable
fun HistoryCard(
    title: String,
    date: String,
    onViewDetails: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .border(1.dp, Color.Gray, RoundedCornerShape(10.dp))
            .padding(16.dp)
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black
            )
            Text(
                text = date,
                color = Color.Gray,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(modifier = Modifier.size(8.dp))

        Button(
            onClick = onViewDetails,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.light_blue))
        ){
            Text(
                text = "View Details",
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview
@Composable
fun HistoryCardPreview() {
    HistoryCard(
        title = "Workout",
        date = "12/12/2021",
        onViewDetails = {}
    )
}