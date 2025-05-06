package com.fitness.screens.macros

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fitness.R
import com.fitness.model.macros.Food

@Composable
fun AddFood(
    onFoodAdded: (Food) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var protein by remember { mutableStateOf("") }
    var fat by remember { mutableStateOf("") }
    var carbs by remember { mutableStateOf("") }

    val isFormValid = name.isNotBlank() &&
            calories.isNotBlank() &&
            protein.isNotBlank() &&
            fat.isNotBlank() &&
            carbs.isNotBlank()



    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = modifier
                .wrapContentHeight()
                .clip(
                    RoundedCornerShape(
                        topStart = 28.dp,
                        topEnd = 28.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                )
                .background(Color.White)
        ) {
            Column(
                modifier = modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Add Food",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )

                    IconButton(
                        onClick = {
                            onClose()
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Close",
                            modifier = Modifier.size(38.dp),
                            tint = Color.Black
                        )
                    }
                }


           OutlinedTextField(
               value = name,
               onValueChange = { name = it },
               label = { Text("Food Name") },
                modifier = Modifier.fillMaxWidth()
           )

           OutlinedTextField(
               value = calories,
               onValueChange = { calories = it },
               label = { Text("Calories (kcal)") },
               modifier = Modifier.fillMaxWidth()
           )

           OutlinedTextField(
               value = protein,
               onValueChange = { protein = it },
               label = { Text("Protein (g)") },
               modifier = Modifier.fillMaxWidth()
           )

           OutlinedTextField(
               value = fat,
               onValueChange = { fat = it },
               label = { Text("Fat (g)") },
               modifier = Modifier.fillMaxWidth()
           )

           OutlinedTextField(
               value = carbs,
               onValueChange = { carbs = it },
               label = { Text("Carbs (g)") },
               modifier = Modifier.fillMaxWidth()
           )

           Button(
               onClick = {
                   val food = Food(name, calories.toInt(), protein.toInt(), fat.toInt(), carbs.toInt())
                   onFoodAdded(food)
                   onClose()
               },
               modifier = Modifier.fillMaxWidth(),
               colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.blue)),
               shape = RoundedCornerShape(16.dp),
               enabled = isFormValid

           ) {
               Text(
                   "Add Food",
                     color = Color.Black,
               )
           }
       }}
   }
}

@Preview(showBackground = true)
@Composable
fun AddFoodPreview() {
    AddFood(onFoodAdded = {},
        onClose = {})
}
