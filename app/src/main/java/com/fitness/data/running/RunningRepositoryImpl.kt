package com.fitness.data.running

import com.fitness.data.auth.AccountService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RunningRepositoryImpl @Inject constructor(
    firestore: FirebaseFirestore,
    accountService: AccountService
) : IRunningRepository {

    private val user = accountService.currentUserId

    private val collection = firestore.collection("users").document(user).collection("running")

    private val _runningSessionsFlow = MutableStateFlow<List<RunningSession>>(emptyList())


    init {
        collection.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val sessions = snapshot.documents.mapNotNull { it.toObject(RunningSession::class.java) }
                _runningSessionsFlow.value = sessions
            }
        }
    }

    override fun getRunningSessions(): Flow<List<RunningSession>> = _runningSessionsFlow

    override suspend fun saveRunningSession(session: RunningSession) {
        collection.document(session.id.toString()).set(session).addOnSuccessListener {
            _runningSessionsFlow.value = _runningSessionsFlow.value.map { if (it.id == session.id) session else it }
        }.await()
    }

    override fun highestId(): Int {
        return _runningSessionsFlow.value.maxOfOrNull { it.id } ?: 0
    }




}
