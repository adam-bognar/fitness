package com.fitness.screens.workout.routine

import CreateRoutineButton
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fitness.R
import com.fitness.data.repository.routine.RoutineViewModel
import com.fitness.model.Routine
import com.fitness.screens.home.TopAppBar

@Composable
fun Routines(
    onStart: (Routine) -> Unit,
    onEdit: (Int) -> Unit,
    viewModel: RoutineViewModel = hiltViewModel()
) {
//    val routines: List<Routine> = listOf(
//        Routine(
//            name = "Routine 1",
//            description = "Description 1",
//            exercises = listOf(
//                Exercise(name = "Bench press 1", id = 1),
//                Exercise(name = "Push ups", id = 2),
//                Exercise(name = "Squat", id = 3),
//                Exercise(name = "Shoulder press", id = 2),
//                Exercise(name = "Barbell curls", id = 3),
//                Exercise(name = "Dumbbell rows", id = 2),
//                Exercise(name = "Lat pull downs", id = 3),
//            ),
//            id = 1
//        ),
//        Routine(
//            name = "Routine 1",
//            description = "Description 1",
//            exercises = listOf(
//                Exercise(name = "Bench press 1", id = 1),
//                Exercise(name = "Push ups", id = 2),
//                Exercise(name = "Squat", id = 3),
//                Exercise(name = "Shoulder press", id = 2),
//            ),
//            id = 1
//        ),
//    )
    var addRoutine by remember { mutableStateOf(false) }
    val routines = viewModel.list.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                alignment = Alignment.Start
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = colorResource(id = R.color.background))
                .padding(16.dp)
            ,
        ){
            Text(
                text = "Workout Routines",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold,
                )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
            ) {
                items(routines.size) { index ->
                    RoutineCard(
                        routine = routines[index],
                        onStart = { onStart(routines[index]) },
                        onEdit = { onEdit(routines[index].id) },
                        onDelete = {
                            viewModel.delete(routines[index])
                        },
                    )
                }
                item {
                    if(!addRoutine){
                        CreateRoutineButton(
                            onClick = {
                                addRoutine = true
                            }
                        )
                    }else{
                        AddRoutineCard(
                            onCreate = { name ->
                                val routine = Routine(
                                    id = viewModel.highestId() + 1,
                                    name = name,
                                    description = "",
                                    exercises = emptyList()
                                )
                                viewModel.upsert(routine)
                                addRoutine = false
                            },
                            onCancel = {
                                addRoutine = false
                            }
                        )
                    }

                }
            }
        }
    }
}



@Preview
@Composable
fun RoutinesPreview() {
    Routines(
        onStart = {},
        onEdit = {},
        )
}