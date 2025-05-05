package com.fitness

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FitnessApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val channel = NotificationChannel(
                "running_channel",
                "Running Session",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)


        }

    }
}