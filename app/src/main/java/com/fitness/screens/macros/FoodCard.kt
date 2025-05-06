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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
fun FoodCard(
    name: String,
    calories: Int,
    protein: Int,
    fat: Int,
    carbs: Int,
    onDelete: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "$calories kcal",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
        ) {
            if (protein > 0) {
                Box(
                    modifier = Modifier
                        .weight(protein.toFloat())
                        .height(8.dp)
                        .background(colorResource(id = R.color.blue))
                )
            }
            if (fat > 0) {
                Box(
                    modifier = Modifier
                        .weight(fat.toFloat())
                        .height(8.dp)
                        .background(colorResource(id = R.color.pink))
                )
            }
            if (carbs > 0) {
                Box(
                    modifier = Modifier
                        .weight(carbs.toFloat())
                        .height(8.dp)
                        .background(Color.Green)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround

        ) {
            Text(
                text = "Protein: $protein g",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )
            Text(
                text = "Fat: $fat g",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )
            Text(
                text = "Carbs: $carbs g",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )
        }

    }
}

@Preview
@Composable
fun FoodCardPreview() {
    FoodCard(
        name = "Chicken Breast", protein = 30, fat = 5, carbs = 2, onDelete = {},
        calories = 200
    )
}
