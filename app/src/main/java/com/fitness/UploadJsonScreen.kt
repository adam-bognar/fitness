package com.fitness

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fitness.data.repository.exercise.GlobalExerciseViewModel
import com.fitness.model.gym.GlobalExercise
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun UploadJsonScreen(
    globalExerciseViewModel: GlobalExerciseViewModel = hiltViewModel()
) {
    var jsonInput by remember { mutableStateOf("") }
    var statusMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Paste JSON Array and Upload", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f) // Makes sure the list is scrollable
                .fillMaxWidth()
        ) {
            item {
                TextField(
                    value = jsonInput,
                    onValueChange = { jsonInput = it },
                    label = { Text("Enter JSON Array") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp) // Limits height so it doesn’t take the whole screen
                        .verticalScroll(rememberScrollState())
                )
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            try {
                val exercises: List<GlobalExercise> = Gson().fromJson(
                    jsonInput,
                    object : TypeToken<List<GlobalExercise>>() {}.type
                )

                if (exercises.isNotEmpty()) {
                    uploadExercisesToFirestore(globalExerciseViewModel, exercises) { success ->
                        statusMessage = if (success) "All exercises uploaded!" else "Upload failed!"
                    }
                } else {
                    statusMessage = "Invalid JSON format!"
                }
            } catch (e: Exception) {
                statusMessage = "Error parsing JSON!"
            }
        }) {
            Text("Upload to Firestore")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(statusMessage, color = Color.Red)
    }
}

fun uploadExercisesToFirestore(
    repository: GlobalExerciseViewModel,
    exercises: List<GlobalExercise>,
    onResult: (Boolean) -> Unit
) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            exercises.forEach { exercise ->
                repository.upsert(exercise) // Upload each exercise
            }
            onResult(true) // Success
        } catch (e: Exception) {
            onResult(false) // Failure
        }
    }
}

