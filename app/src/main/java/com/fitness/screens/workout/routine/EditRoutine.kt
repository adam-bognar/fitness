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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fitness.R
import com.fitness.data.repository.routine.RoutineViewModel
import com.fitness.model.Exercise
import com.fitness.model.Routine
import com.fitness.screens.home.TopAppBar
import com.fitness.screens.workout.exercise.AddExerciseButton
import com.fitness.screens.workout.exercise.ExerciseCard
import com.fitness.screens.workout.exercise.SelectExercisePopUp

@Composable
fun EditRoutine(
    routine: Routine,
    onBack: () -> Unit,
    viewModel: RoutineViewModel = hiltViewModel()
) {
    var addExercise by remember { mutableStateOf(false) }


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
                    text = routine.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold,
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(routine.exercises.size) { index ->
                        ExerciseCard(
                            name = routine.exercises[index].name,
                            onDelete = {
                                routine.exercises.toMutableList().removeAt(index)
                                viewModel.upsert(routine)
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
        routine = Routine(
            name = "Routine 1",
            description = "Description 1",
            exercises = listOf(
                Exercise(name = "ize", id = 1),
                Exercise(name = "ize", id = 1),
                Exercise(name = "ize", id = 1),
                Exercise(name = "ize", id = 1),
                Exercise(name = "ize", id = 1),
                Exercise(name = "ize", id = 1),),

            id =   1),
        onBack = {}
    )


}
