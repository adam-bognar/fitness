package com.fitness.screens.profile

import ExerciseProgressChart
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fitness.R
import com.fitness.data.repository.session.SessionViewModel
import com.fitness.data.running.RunningViewModel
import com.fitness.model.gym.ExerciseLog
import com.fitness.navigation.Screen
import com.fitness.screens.home.BottomBar
import com.fitness.screens.home.TopAppBar
import com.google.firebase.Timestamp
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

data class ExerciseProgressPoint(val date: Timestamp, val value: Double)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavigate: (String) -> Unit,
    sessionViewModel: SessionViewModel = hiltViewModel(),
    runningViewModel: RunningViewModel = hiltViewModel(),

) {
    var workoutActive by remember { mutableStateOf(true) }

    val sessions by sessionViewModel.list.collectAsState()
    val runningSession by runningViewModel.runningSessions.collectAsState()

    val uniqueExerciseNames by remember {
        derivedStateOf {
            sessions
                .flatMap { it.log }
                .map { it.name }
                .filter { it.isNotBlank() }
                .distinct()
                .sorted()
        }
    }

    var expanded by remember { mutableStateOf(false) }
    var selectedExerciseName by remember { mutableStateOf<String?>(null) }

    val filteredSessions by remember(sessions, selectedExerciseName) {
        derivedStateOf {
            val selectedName = selectedExerciseName
            if (!workoutActive || selectedName == null) {
                sessions
            } else {
                sessions.filter { session ->
                    session.log.any { exercise -> exercise.name == selectedName }
                }
            }
        }
    }

    var exerciseProgressDataState by remember { mutableStateOf<List<ExerciseLog>>(emptyList()) }
    var isCalculatingProgress by remember { mutableStateOf(false) }

    LaunchedEffect(sessions, selectedExerciseName, workoutActive) {
        val selectedName = selectedExerciseName
        if (!workoutActive || selectedName == null) {
            exerciseProgressDataState = emptyList()
            isCalculatingProgress = false
            return@LaunchedEffect
        }

        isCalculatingProgress = true
        val calculatedData = withContext(Dispatchers.Default) {
            sessions
                .filter { it.date != null }
                .sortedBy { it.date }
                .mapNotNull { session ->
                    val exercisePerformance =
                        session.log.find { it.name == selectedName }?.lastLog

                    exercisePerformance
                }
        }
        exerciseProgressDataState = calculatedData
        isCalculatingProgress = false
    }

    LaunchedEffect(uniqueExerciseNames) {
        if (selectedExerciseName != null && selectedExerciseName !in uniqueExerciseNames) {
            selectedExerciseName = null
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                alignment = Alignment.Start,
            )
        },
        bottomBar = {
            BottomBar(
                onClick = onNavigate
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(colorResource(id = R.color.background))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = "Profile",
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color.Gray)
                                .border(1.dp, Color.White, CircleShape)
                                .size(100.dp)
                        )

                        Spacer(modifier = Modifier.size(8.dp))

                        Text(text = "Charles Leclerc", style = MaterialTheme.typography.titleMedium)

                        Button(
                            onClick = { /*TODO*/ },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(Color.White),
                            modifier = Modifier
                                .border(1.dp, Color.Gray, RoundedCornerShape(10.dp))
                                .height(40.dp)
                        ) {
                            Text(
                                "Sign out",
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = "Back",
                                tint = Color.Black
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TotalCard(
                            total = sessions.size,
                            title = "Total Workouts",
                        )
                        TotalCard(
                            total = runningSession.size,
                            title = "Total Runs",
                        )
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "Activity history",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                workoutActive = true
                                selectedExerciseName = null
                                expanded = false
                            },
                            shape = RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp),
                            colors = if(!workoutActive) ButtonDefaults.buttonColors(Color.White) else ButtonDefaults.buttonColors(colorResource(R.color.purple)),
                            modifier = Modifier
                                .weight(1f)
                                .border(1.dp, Color.Gray, RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp))
                                .height(40.dp)
                        ) {
                            Text("Workouts", color = Color.Black)
                        }

                        Button(
                            onClick = {
                                workoutActive = false
                                selectedExerciseName = null
                                expanded = false
                            },
                            shape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp),
                            colors = if(workoutActive) ButtonDefaults.buttonColors(Color.White) else ButtonDefaults.buttonColors(colorResource(R.color.purple)),
                            modifier = Modifier
                                .weight(1f)
                                .border(1.dp, Color.Gray, RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp))
                                .height(40.dp)
                        ) {
                            Text("Running", color = Color.Black)
                        }
                    }

                    if (workoutActive && uniqueExerciseNames.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextField(
                                value = selectedExerciseName ?: "Select Exercise",
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Exercise") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                uniqueExerciseNames.forEach { exerciseName ->
                                    DropdownMenuItem(
                                        text = { Text(exerciseName) },
                                        onClick = {
                                            selectedExerciseName = exerciseName
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    if (workoutActive && selectedExerciseName != null) {
                        if (isCalculatingProgress) {
                            Spacer(modifier = Modifier.height(200.dp).fillMaxWidth())
                        } else {
                            ExerciseProgressChart(
                                data = exerciseProgressDataState,
                                exerciseName = selectedExerciseName ?: "Exercise"
                            )
                            Log.d("ExerciseProgress", "Data: $exerciseProgressDataState")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (workoutActive) {
                            items(filteredSessions.size) { index ->
                                val session = filteredSessions[index]
                                val date = session.date?.let { formatFirebaseTimestamp(it) } ?: "Error"
                                HistoryCard(
                                    title = session.name,
                                    date = date,
                                    onViewDetails = {}
                                )
                            }
                        } else {
                            items(runningSession.size) { index ->
                                val session = runningSession[index]
                                val date = session.date?.let { formatFirebaseTimestamp(it) } ?: "Error"

                                val sessionJson = Gson().toJson(session)
                                HistoryCard(
                                    title = "Running",
                                    date = date,
                                    onViewDetails = {
                                        onNavigate("${Screen.MAP.route}/$sessionJson")
                                    }
                                )
                            }
                        }

                    }
                }
            }
        }
    )
}


fun formatFirebaseTimestamp(timestamp: Timestamp): String {
    val date = timestamp.toDate()
    val formatter = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    return formatter.format(date)
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(onNavigate = {})
}

