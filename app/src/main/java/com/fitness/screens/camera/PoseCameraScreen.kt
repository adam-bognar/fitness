package com.fitness.screens.camera

import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.fitness.pose.ExerciseType
import com.fitness.pose.getPoseDetector
import com.fitness.pose.isDoingPullUp
import com.fitness.pose.isDoingPushUp
import com.fitness.pose.isJumpingJackOpen
import com.fitness.pose.isSquatting
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.PoseLandmark
import java.util.concurrent.Executors

@OptIn(ExperimentalGetImage::class)
@Composable
fun PoseCameraScreen() {
    val context = LocalContext.current
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    val previewView = remember { PreviewView(context) }
    val poseDetector = remember { getPoseDetector() }
    val executor = remember { Executors.newSingleThreadExecutor() }

    var imageWidth by remember { mutableStateOf(1f) }
    var imageHeight by remember { mutableStateOf(1f) }
    var landmarks by remember { mutableStateOf<List<PoseLandmark>>(emptyList()) }

    var selectedExercise by remember { mutableStateOf(ExerciseType.SQUAT) }
    var repCount by remember { mutableStateOf(0) }
    var wasInPosition by remember { mutableStateOf(false) }

    val view = LocalView.current
    DisposableEffect(Unit) {
        view.keepScreenOn = true
        onDispose {
            view.keepScreenOn = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(factory = { previewView }, modifier = Modifier.fillMaxSize()) {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

                val analyzer = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also {
                        it.setAnalyzer(executor) { imageProxy ->
                            val mediaImage = imageProxy.image
                            if (mediaImage != null) {
                                val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                                poseDetector.process(image)
                                    .addOnSuccessListener { pose ->
                                        landmarks = pose.allPoseLandmarks

                                        if (landmarks.isNotEmpty()) {
                                            val inPosition = when (selectedExercise) {
                                                ExerciseType.SQUAT -> isSquatting(pose)
                                                ExerciseType.PUSHUP -> isDoingPushUp(pose)
                                                ExerciseType.JUMPING_JACK -> isJumpingJackOpen(pose)
                                                ExerciseType.PULLUP -> isDoingPullUp(pose)
                                            }

                                            if (inPosition && !wasInPosition) {
                                                wasInPosition = true
                                            } else if (!inPosition && wasInPosition) {
                                                repCount++
                                                wasInPosition = false
                                                Log.d("Pose", "${selectedExercise.label} rep: $repCount")
                                            }
                                        }
                                    }
                                    .addOnCompleteListener {
                                        imageProxy.close()
                                    }

                                imageWidth = imageProxy.width.toFloat()
                                imageHeight = imageProxy.height.toFloat()
                            }
                        }
                    }

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, analyzer)
            }, ContextCompat.getMainExecutor(context))
        }

        // Exercise Switcher Buttons (top row)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ExerciseType.values().forEach { type ->
                Button(
                    onClick = {
                        selectedExercise = type
                        repCount = 0
                        wasInPosition = false
                    },
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(type.label, fontSize = 12.sp)
                }
            }
        }

        // Reps display (bottom center)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = "Reps: $repCount",
                fontSize = 32.sp
            )
        }
    }
}
