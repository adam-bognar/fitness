package com.fitness.data.steps

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.fitness.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class StepService : Service() {

    @Inject
    lateinit var stepTrackingRepository: IStepTrackingRepository

    private var serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private lateinit var notificationManager: NotificationManager

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        when (intent?.action) {
            Actions.START.toString() -> {
                start()
                stepTrackingRepository.startTracking()
                observeStepCount()
            }
            Actions.STOP.toString() -> {
                stopSelf()
            }
        }

        return START_STICKY
    }

    private fun start() {
        val notification = createNotification(0)
        startForeground(1, notification)
    }

    private fun observeStepCount() {
        serviceScope.launch {
            stepTrackingRepository.stepCount.collectLatest { steps ->
                updateNotification(steps)
            }
        }
    }

    private fun createNotification(steps: Int) =
        NotificationCompat.Builder(this, "step_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("FitTrack")
            .setContentText("$steps steps")
            .setSilent(true)
            .build()

    private fun updateNotification(steps: Int) {
        val notification = createNotification(steps)
        notificationManager.notify(1, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    enum class Actions {
        START,
        STOP
    }
}
