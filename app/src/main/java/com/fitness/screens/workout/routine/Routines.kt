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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fitness.R
import com.fitness.data.repository.routine.RoutineViewModel
import com.fitness.model.gym.Routine
import com.fitness.screens.home.BottomBar
import com.fitness.screens.home.TopAppBar

@Composable
fun Routines(
    onStart: (Routine) -> Unit,
    onEdit: (Int) -> Unit,
    viewModel: RoutineViewModel = hiltViewModel(),
    onClick: (String) -> Unit
) {
    var addRoutine by remember { mutableStateOf(false) }
    val routines = viewModel.list.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                alignment = Alignment.Start
            )
        },
        bottomBar = {
            BottomBar (
                onClick = onClick
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
                color = Color.Black
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
        onClick = {}
        )
}