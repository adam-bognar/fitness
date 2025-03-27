package com.fitness.data

import android.content.Context
import android.content.Intent
import android.util.Log
import com.fitness.data.running.RunningService
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageEvent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MessageReceiver @Inject constructor(
    @ApplicationContext private val context: Context
) : MessageClient.OnMessageReceivedListener {

    override fun onMessageReceived(p0: MessageEvent) {
        val message = String(p0.data, Charsets.UTF_8)
        Log.d("WearOSLog", "Message received: $message")

        val intent = Intent(context, RunningService::class.java).apply {
            action = when (message) {
                "started" -> RunningService.ACTION_START
                "finished" -> RunningService.ACTION_STOP
                else -> null
            }
        }
        if (intent.action != null) {
            context.startService(intent)
        }
    }
}
