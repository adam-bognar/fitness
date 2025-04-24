package com.fitness.screens.workout.session

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.fitness.R
import com.fitness.data.repository.exercise.UserExerciseViewModel
import com.fitness.data.repository.routine.RoutineViewModel
import com.fitness.data.repository.session.SessionViewModel
import com.fitness.model.gym.Exercise
import com.fitness.model.gym.Routine
import com.fitness.model.gym.Session
import com.fitness.screens.home.TopAppBar
import com.fitness.screens.workout.exercise.SelectExercisePopUp
import com.google.firebase.Timestamp


@Composable
fun WorkoutPage(
    routine: Routine,
    onNavigateBack: () -> Unit,
    routineViewModel: RoutineViewModel = hiltViewModel(),
    exerciseViewModel: UserExerciseViewModel = hiltViewModel(),
    sessionViewModel: SessionViewModel = hiltViewModel(),
    onNavigateCamera: (Pair<String, Int>) -> Unit,
    navController: NavHostController,

    ) {
    var exercises by remember { mutableStateOf(routineViewModel.getRoutine(routine.id).exercises) }
    Log.d("WorkoutPage", "exercises: $exercises")
    val showTimer = remember { mutableStateOf(false) }
    var showTimerSettings by remember { mutableStateOf(false) }
    var isSwitched by remember { mutableStateOf(true) }
    var timerTime by remember { mutableIntStateOf(120) }
    var addExercise by remember { mutableStateOf(false) }

    val repsResultLiveData = navController
        .currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<RepsResult>("repsResult")

    val repsResult = repsResultLiveData?.observeAsState()?.value


  LaunchedEffect(repsResult) {
      repsResultLiveData?.value?.let {
          navController.currentBackStackEntry
              ?.savedStateHandle
              ?.remove<RepsResult>("repsResult")
      }

   }




    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    alignment = Alignment.CenterHorizontally,
                    onBack = onNavigateBack,
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(colorResource(R.color.background))
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = routine.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Row {
                        IconButton(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            onClick = {
                                showTimerSettings = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Timer,
                                contentDescription = "Timer",
                            )
                        }
                        Button(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .wrapContentSize(),
                            onClick = {
                                routineViewModel.upsert(routine.copy(exercises = exercises))
                                exercisesToFireBase(exercises, exerciseViewModel)
                                sessionToFirebase(routine.name, exercises, sessionViewModel)
                                onNavigateBack()
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.light_blue))
                        ) {
                            Text(
                                "Finish",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }


                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(exercises.size) { index ->
                        WorkoutItem(
                            exercise = exercises[index],
                            onUpdate = { updatedExercise ->
                                exercises = exercises.toMutableList().apply {
                                    set(index, updatedExercise)
                                }
                            },
                            onDoneClick = { showTimer.value = true },
                            onNavigate = { pair ->
                                onNavigateCamera(pair)
                            },
                            cameraResult = (if(repsResult?.exerciseName == exercises[index].name) repsResult else null)
                        )

                    }

                    item {
                        TextButton(
                            onClick = {
                                addExercise = true
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp, bottom = 10.dp),
                        ) {
                            Text(
                                text = "Add new exercise",
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                    color = colorResource(id = R.color.button_color)
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

            }

        }

        if (!isSwitched) {
            showTimer.value = false
        }

        if (showTimer.value && isSwitched) {
            Timer(
                initialTime = timerTime,
                onDismiss = { showTimer.value = false },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }

        if (showTimerSettings) {
            TimerDialog(
                onDismissRequest = { showTimerSettings = false },
                modifier = Modifier
                    .align(Alignment.Center),
                onSwitchChange = { isChecked ->
                    isSwitched = isChecked
                },
                onTimeChange = { time ->
                    timerTime = time
                },
                initialTime = timerTime,
                switchState = isSwitched
            )
        }

        if (addExercise) {
            SelectExercisePopUp(
                onExerciseSelected = {
                    exercises = exercises + it
                    routineViewModel.upsert(routine.copy(exercises = exercises))
                    exerciseViewModel.upsertUserExercise(it)
                    addExercise = false
                },
                onDismiss = { addExercise = false },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }

}

fun sessionToFirebase(name: String, exercises: List<Exercise>, sessionViewModel: SessionViewModel) {
    val session = Session(
        id = sessionViewModel.highestId() + 1,
        name = name,
        date = Timestamp.now(),
        duration = 0,
        log = exercises
    )
    Log.d("sessionToFirebase", "Session: $session")
    sessionViewModel.upsert(session)
}

fun exercisesToFireBase(exercises: List<Exercise>, exerciseViewModel: UserExerciseViewModel) {
    for (exercise in exercises) {
        exerciseViewModel.upsertUserExercise(exercise)
        Log.d("exercisesToFireBase", "Exercise: $exercise")
    }
}


@Preview(showBackground = true)
@Composable
fun WorkoutPagePreview() {
    val exercises = listOf(
        Exercise(id = 0, name = "Exercise 1"),
        Exercise(id = 0, name = "Exercise 2"),
        Exercise(id = 0, name = "Exercise 3"),
    )

    val sampleRoutine = Routine(id = 0, name = "Routine 1", description = "", exercises = exercises)


    WorkoutPage(
        routine = sampleRoutine,
        onNavigateBack = {},
        onNavigateCamera = {},
        navController = NavHostController(LocalContext.current)
    )
}
