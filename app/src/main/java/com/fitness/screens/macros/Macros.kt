package com.fitness.screens.macros

import FakeMacrosRepository
import FakeUserInformationRepository
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fitness.R
import com.fitness.data.repository.macros.MacrosViewModel
import com.fitness.data.repository.user_information.UserInfoViewModel
import com.fitness.model.macros.ActivityLevel
import com.fitness.model.macros.CurrentMacros
import com.fitness.model.macros.DailyMacroRecord
import com.fitness.model.macros.Gender
import com.fitness.model.macros.Goal
import com.fitness.model.macros.TargetMacros
import com.fitness.model.macros.UserInformation
import com.fitness.screens.home.BottomBar
import com.fitness.screens.home.TopAppBar
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun Macros(
    onNavigateTo: (String) -> Unit,
    userInfoViewModel: UserInfoViewModel = hiltViewModel(),
    macrosViewModel: MacrosViewModel = hiltViewModel()
) {
    var addFood by remember { mutableStateOf(false) }
    var settings by remember { mutableStateOf(false) }

    val user by userInfoViewModel.userInformation.collectAsState()

    val currentDate =
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Timestamp.now().toDate())

    val macros by macrosViewModel.macros.collectAsState()

    if (macros.isNotEmpty() && macros.last().let {
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it.date.toDate())
        } != currentDate) {
        val newMacro = DailyMacroRecord(
            id = macrosViewModel.highestId() + 1,
            date = Timestamp.now(),
            targetMacros = calculateMacros(user),
            currentMacros = CurrentMacros(),
            foodList = emptyList()
        )
        macrosViewModel.upsert(newMacro)
    }
    val today = if (macros.isNotEmpty()) {
        macros.last()
    } else {
        DailyMacroRecord(
            id = 1,
            date = Timestamp.now(),
            targetMacros = calculateMacros(user),
            currentMacros = CurrentMacros(),
            foodList = emptyList()
        )
    }


//user.weight.toInt() == 0 && user.age == 0 && user.height.toInt() == 0
    if (user.weight.toInt() == 0 && user.age == 0 && user.height.toInt() == 0) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (settings || addFood) {
                        Color.Black.copy(alpha = 0.5f)
                    } else {
                        Color.Transparent
                    }
                )
        )
        {
            Scaffold(
                topBar = {
                    TopAppBar(alignment = Alignment.Start)
                },
                bottomBar = {
                    BottomBar(onClick = onNavigateTo)
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .background(color = colorResource(id = R.color.background))
                        .padding(horizontal = 16.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Macros",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        IconButton(
                            onClick = {
                                settings = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings",
                                Modifier.size(28.dp),
                                tint = Color.Black
                            )
                        }
                    }
                    Text(
                        text = "Please fill in your information to get started",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                }
            }

            AnimatedVisibility(
                visible = settings,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Settings(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    onClose = { settings = false },
                    onSave = {
                        userInfoViewModel.upsert(it)

                        today.targetMacros = calculateMacros(it)
                        macrosViewModel.upsert(today)
                        settings = false
                    }
                )
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (settings || addFood) {
                        Color.Black.copy(alpha = 0.5f)
                    } else {
                        Color.Transparent
                    }
                )
        )
        {
            Scaffold(
                topBar = {
                    TopAppBar(alignment = Alignment.Start)

                },
                bottomBar = {

                    BottomBar(onClick = onNavigateTo)

                },
                floatingActionButton = {

                    FloatingActionButton(
                        onClick = { addFood = true },
                        containerColor = colorResource(id = R.color.light_blue),
                        shape = CircleShape
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Macro",
                            tint = Color.Black,
                        )
                    }

                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .background(color = colorResource(id = R.color.background))
                        .padding(horizontal = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Macros",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        IconButton(
                            onClick = {
                                settings = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings",
                                Modifier.size(28.dp),
                                tint = Color.Black
                            )
                        }
                    }

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item(1) {
                            MacroCard(
                                title = "Calories",
                                value = today.currentMacros.currentCalories.toFloat(),
                                target = today.targetMacros.targetCalories,
                                unit = "kcal"
                            )
                        }
                        item(2) {
                            MacroCard(
                                title = "Protein",
                                value = today.currentMacros.currentProtein.toFloat(),
                                target = today.targetMacros.targetProtein,
                                unit = "g"
                            )
                        }
                        item(3) {


                            MacroCard(
                                title = "Fat",
                                value = today.currentMacros.currentFat.toFloat(),
                                target = today.targetMacros.targetFat,
                                unit = "g"
                            )
                        }

                        item(4) {
                            MacroCard(
                                title = "Carbs",
                                value = today.currentMacros.currentCarbs.toFloat(),
                                target = today.targetMacros.targetCarbs,
                                unit = "g"
                            )
                        }
                    }


                    Text(
                        text = "Today's foods",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp),
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(today.foodList.size) { index ->
                            FoodCard(
                                name = today.foodList[index].name,
                                calories = today.foodList[index].calories,
                                protein = today.foodList[index].protein,
                                fat = today.foodList[index].fat,
                                carbs = today.foodList[index].carbs,
                                onDelete = {
                                    val updatedFoodList = today.foodList.toMutableList().apply { removeAt(index) }
                                    today.currentMacros.currentProtein -= today.foodList[index].protein
                                    today.currentMacros.currentFat -= today.foodList[index].fat
                                    today.currentMacros.currentCarbs -= today.foodList[index].carbs
                                    today.currentMacros.currentCalories -= today.foodList[index].calories

                                    val updatedToday = today.copy(foodList = updatedFoodList)
                                    macrosViewModel.upsert(updatedToday)
                                }
                            )
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = addFood,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                AddFood(
                    onFoodAdded = { food ->
                        today.foodList += food

                        today.currentMacros.currentProtein += food.protein
                        today.currentMacros.currentFat += food.fat
                        today.currentMacros.currentCarbs += food.carbs
                        today.currentMacros.currentCalories += food.calories

                        macrosViewModel.upsert(today)

                        addFood = false

                    },
                    modifier = Modifier.align(Alignment.BottomCenter),
                    onClose = { addFood = false }
                )
            }

            AnimatedVisibility(
                visible = settings,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Settings(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    onClose = { settings = false },
                    onSave = {
                        userInfoViewModel.upsert(it)

                        today.targetMacros = calculateMacros(it)
                        macrosViewModel.upsert(today)
                        settings = false
                    }
                )
            }
        }
    }


}

fun calculateMacros(
    user: UserInformation
): TargetMacros {

    val bmr = if (user.gender == Gender.MALE) {
        (9.9 * user.weight) + (6.25 * user.height) - (4.92 * user.age) + 5
    } else {
        (9.9 * user.weight) + (6.25 * user.height) - (4.92 * user.age) - 161
    }

    val multiplier = when (user.activityLevel) {
        ActivityLevel.SEDENTARY -> 1.2
        ActivityLevel.LIGHTLY_ACTIVE -> 1.375
        ActivityLevel.MODERATELY_ACTIVE -> 1.55
        ActivityLevel.ACTIVE -> 1.725
        ActivityLevel.VERY_ACTIVE -> 1.9
    }

    var tdee = bmr * multiplier

    tdee = when (user.goal) {
        Goal.LOSE_WEIGHT -> tdee - 500
        Goal.GAIN_WEIGHT -> tdee + 300
        else -> tdee
    }

    val proteinGrams = (2.0 * user.weight).toInt()
    val fatGrams = ((tdee * 0.25) / 9).toInt()
    val carbGrams = ((tdee - (proteinGrams * 4 + fatGrams * 9)) / 4).toInt()

    return TargetMacros(tdee.toInt(), proteinGrams, fatGrams, carbGrams)
}


@Preview(showBackground = true)
@Composable
fun MacrosPreview() {
    val mockUserInfoViewModel = UserInfoViewModel(
        userInfoRepository = FakeUserInformationRepository()
    )
    val mockMacrosViewModel = MacrosViewModel(
        macrosRepository = FakeMacrosRepository()
    )

    Macros(
        onNavigateTo = {},
        userInfoViewModel = mockUserInfoViewModel,
        macrosViewModel = mockMacrosViewModel
    )
}
