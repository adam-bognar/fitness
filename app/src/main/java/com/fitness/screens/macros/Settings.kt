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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import com.fitness.model.macros.ActivityLevel
import com.fitness.model.macros.Gender
import com.fitness.model.macros.Goal
import com.fitness.model.macros.UserInformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings(
    onSave: (UserInformation) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {

    var age by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf(Gender.MALE) }
    var activityLevel by remember { mutableStateOf(ActivityLevel.SEDENTARY) }
    var goal by remember { mutableStateOf(Goal.LOSE_WEIGHT) }

    var genderExpanded by remember { mutableStateOf(false) }
    var activityLevelExpanded by remember { mutableStateOf(false) }
    var goalExpanded by remember { mutableStateOf(false) }

    val isFormValid = age.isNotBlank() &&
            weight.isNotBlank() &&
            height.isNotBlank()



    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = modifier
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
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Edit your settings",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
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
                    value = age,
                    onValueChange = { age = it },
                    label = { Text("Age") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = { Text("Weight") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = height,
                    onValueChange = { height = it },
                    label = { Text("Height") },
                    modifier = Modifier.fillMaxWidth()
                )

                ExposedDropdownMenuBox(
                    expanded = genderExpanded,
                    onExpandedChange = { genderExpanded = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = gender.toString(),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Gender") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = genderExpanded)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = genderExpanded,
                        onDismissRequest = { genderExpanded = false },
                        modifier = Modifier.exposedDropdownSize()
                    ) {
                        Gender.entries.forEach {
                            DropdownMenuItem(
                                onClick = {
                                    gender = it
                                    genderExpanded = false
                                },
                                text = {
                                    Text(it.toString())
                                }
                            )
                        }
                    }

                }
                ExposedDropdownMenuBox(
                    expanded = activityLevelExpanded,
                    onExpandedChange = { activityLevelExpanded = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = activityLevel.toString(),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Activity") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = activityLevelExpanded)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = activityLevelExpanded,
                        onDismissRequest = { activityLevelExpanded = false },
                        modifier = Modifier.exposedDropdownSize()
                    ) {
                        ActivityLevel.entries.forEach {
                            DropdownMenuItem(
                                onClick = {
                                    activityLevel = it
                                    activityLevelExpanded = false
                                },
                                text = {
                                    Text(
                                        it.toString(),
                                        color = Color.Black
                                    )
                                }
                            )
                        }
                    }

                }
                ExposedDropdownMenuBox(
                    expanded = goalExpanded,
                    onExpandedChange = { goalExpanded = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = goal.toString(),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Goal") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = goalExpanded)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = goalExpanded,
                        onDismissRequest = { goalExpanded = false },
                        modifier = Modifier.exposedDropdownSize()
                    ) {
                        Goal.entries.forEach {
                            DropdownMenuItem(
                                onClick = {
                                    goal = it
                                    goalExpanded = false
                                },
                                text = {
                                    Text(
                                        it.toString(),
                                        color = Color.Black)
                                }
                            )
                        }
                    }

                }

                Button(
                    onClick = {
                        onSave(
                            UserInformation(
                                age = age.toInt(),
                                weight = weight.toDouble(),
                                height = height.toDouble(),
                                gender = gender,
                                activityLevel = activityLevel,
                                goal = goal
                            )
                        )
                        onClose()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.blue)),
                    shape = RoundedCornerShape(16.dp),
                    enabled = isFormValid

                ) {
                    Text(
                        "Save",
                        color = Color.Black,
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewSettings() {
    Settings(
        onSave = {},
        onClose = {}
    )
}