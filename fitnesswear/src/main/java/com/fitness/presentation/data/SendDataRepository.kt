package com.fitness.presentation.data

import android.content.Context
import android.util.Log
import com.google.android.gms.wearable.Wearable
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SendDataRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : ISendDataRepository {
    companion object {
        const val MESSAGE_PATH_SEND = "/send_message"
        const val MESSAGE_PATH_RECEIVE = "/receive_message"
    }

    override fun sendMessage(message: String) {
        Wearable.getNodeClient(context).connectedNodes.addOnSuccessListener { nodes ->
            for (node in nodes) {
                if (node.isNearby) {
                    Wearable.getMessageClient(context).sendMessage(
                        node.id,
                        MESSAGE_PATH_SEND,
                        message.toByteArray(Charsets.UTF_8)
                    )
                        .addOnSuccessListener {
                            Log.d("WearOSLog", "Message sent successfully to ${node.id}")
                            Log.d("WearOSLog", message)
                        }
                        .addOnFailureListener { exception ->
                            Log.e("WearOSLog", "Failed to send message to ${node.id}", exception)
                            Log.d("WearOSLog", message)

                        }
                } else {
                    Log.d("WearOSLog", "Node ${node.id} is not reachable")
                }
            }
        }.addOnFailureListener { exception ->
            Log.e("WearOSLog", "Failed to get connected nodes", exception)
        }
    }
}