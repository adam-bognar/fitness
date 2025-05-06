package com.fitness.screens.workout.routine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.fitness.data.repository.exercise.UserExerciseViewModel
import com.fitness.data.repository.routine.RoutineViewModel
import com.fitness.model.gym.Routine
import com.fitness.screens.home.TopAppBar
import com.fitness.screens.workout.exercise.AddExerciseButton
import com.fitness.screens.workout.exercise.ExerciseCard
import com.fitness.screens.workout.exercise.SelectExercisePopUp

@Composable
fun EditRoutine(
    routineID: Int,
    onBack: () -> Unit,
    routineViewModel: RoutineViewModel = hiltViewModel(),
    exerciseViewModel: UserExerciseViewModel = hiltViewModel(),

) {
    var addExercise by remember { mutableStateOf(false) }
    val routine by routineViewModel.list.collectAsState()
    val currentRoutine = routine.firstOrNull { it.id == routineID } ?: Routine()




    Box{
        Scaffold(
            topBar = {
                TopAppBar(
                    alignment = Alignment.CenterHorizontally,
                    onBack = onBack
                )
            },

            ) { innerPadding ->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = colorResource(id = R.color.background))
                .padding(16.dp)
            ) {
                Text(
                    text = currentRoutine.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(currentRoutine.exercises.size) { index ->
                        ExerciseCard(
                            name = currentRoutine.exercises[index].name,
                            onDelete = {
                                val updatedExercises = currentRoutine.exercises.toMutableList().apply { removeAt(index) }
                                val updatedRoutine = currentRoutine.copy(exercises = updatedExercises)
                                routineViewModel.upsert(updatedRoutine)
                            }
                        )
                    }
                    item {
                        AddExerciseButton(
                            onClick = { addExercise = true }
                        )
                    }
                }
            }
        }

        if(addExercise){
            SelectExercisePopUp(
                onExerciseSelected = {
                    currentRoutine.exercises += it
                    routineViewModel.upsert(currentRoutine)
                    exerciseViewModel.upsertUserExercise(it)
                    addExercise = false

                },
                onDismiss = { addExercise = false },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }

}



@Preview
@Composable
fun EditRoutinePreview(){
    EditRoutine(
        1,
        onBack = {},
    )


}
