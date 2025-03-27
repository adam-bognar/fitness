package com.fitness.data.running

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import com.fitness.R
import com.fitness.data.location.DefaultLocationClient
import com.fitness.data.location.LocationClient
import com.fitness.data.steps.IStepTrackingRepository
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RunningService : Service() {

    @Inject
    lateinit var stepTrackingRepository: IStepTrackingRepository

    @Inject
    lateinit var runningRepository: IRunningRepository


    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private lateinit var locationClient: LocationClient
    private lateinit var notificationManager: NotificationManager


    private var elapsedTime: Int = 0
    private var currentSteps: Int = 0
    private var currentLocation: String = "Waiting for location..."
    private var coords: MutableList<LatLng> = mutableListOf()

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> startTracking()
            ACTION_STOP -> stopTracking()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun stopTracking() {
        Log.d("RunningService", "stopTracking called")

        val mycoords = coords.map { MyLatLng(it.latitude, it.longitude) }
        val distance = RunningSession().calculateTotalDistance(coords)

        val id = runningRepository.highestId()
        Log.d("RunningService", "Highest ID: $id")


        val session = RunningSession(
            id = runningRepository.highestId() + 1,
            timestamp = elapsedTime.toLong(),
            steps = currentSteps,
            coords = mycoords,
            distance = distance,
            date = Timestamp.now()
        )

        serviceScope.launch {
            try {
                runningRepository.saveRunningSession(session)
                Log.d("RunningService", "Session saved: $session")
            } catch (e: Exception) {
                Log.e("RunningService", "Failed to save session", e)
            } finally {
                Log.d("RunningService", "Stopping service")
                stopSelf()
            }
        }

    }

    private var startTime = 0L

    private fun startTracking() {
        Log.d("RunningService", "startTracking called")

        startForeground(1, createNotification())

        startTime = SystemClock.elapsedRealtime() // Capture start time

        serviceScope.launch {
            while (true) {
                elapsedTime = ((SystemClock.elapsedRealtime() - startTime) / 1000).toInt()
                updateNotification()
                delay(1000L) // Update every second
            }
        }

        // Start step tracking
        stepTrackingRepository.startTracking()
        serviceScope.launch {
            stepTrackingRepository.stepCount.collectLatest { steps ->
                currentSteps = steps
                updateNotification()
            }
        }

        // Start location tracking
        locationClient.getLocationUpdates(5000L) // Update every 5 seconds
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                currentLocation = "Lat: ${location.latitude}, Lon: ${location.longitude}"
                coords.add(LatLng(location.latitude, location.longitude))
                updateNotification()
            }
            .launchIn(serviceScope)


    }

    private fun createNotification(): android.app.Notification {
        return NotificationCompat.Builder(this, "running_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Running Session")
            .setContentText("Time: ${formatElapsedTime(elapsedTime)} | Steps: $currentSteps")
            .setSilent(true)
            .setOngoing(true)
            .build()
    }

    private fun updateNotification() {
        val notification = createNotification()
        notificationManager.notify(1, notification)
    }

    private fun formatElapsedTime(seconds: Int): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        return String.format("%02d:%02d:%02d", hours, minutes, secs)
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }
}
