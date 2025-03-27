package com.fitness

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import com.fitness.navigation.NavGraph
import com.fitness.ui.theme.FitnessTheme
import com.fitness.data.MessageReceiver
import com.google.android.gms.wearable.Wearable
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val messageClient by lazy { Wearable.getMessageClient(this)}
    @Inject
    lateinit var messageReceiver: MessageReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACTIVITY_RECOGNITION,
                    android.Manifest.permission.FOREGROUND_SERVICE,
                    android.Manifest.permission.POST_NOTIFICATIONS,
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION

                ),
                0
            )
        }

        setContent {
            FitnessTheme {
                NavGraph()
            }
        }

        messageClient.addListener(messageReceiver)

    }

    override fun onDestroy() {
        messageClient.removeListener(messageReceiver)
        super.onDestroy()
    }
}