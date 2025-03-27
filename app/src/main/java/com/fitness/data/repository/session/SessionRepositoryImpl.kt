package com.fitness.data.repository.session

import com.fitness.data.auth.AccountService
import com.fitness.model.gym.Session
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    db: FirebaseFirestore,
    accountService: AccountService
) : ISessionRepository {

    private val user = accountService.currentUserId


    private val collection = db.collection("users").document(user).collection("sessions")

    private val _sessionsFlow = MutableStateFlow<List<Session>>(emptyList())

    init {
        collection.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val sessions = snapshot.documents.mapNotNull { it.toObject(Session::class.java) }
                _sessionsFlow.value = sessions
            }
        }
    }

    override fun getAllSessions(): Flow<List<Session>> = _sessionsFlow.asStateFlow()

    override suspend fun upsert(session: Session) {
        collection.document(session.id.toString()).set(session)
    }


    override suspend fun highestId(): Int {
        return _sessionsFlow.value.maxOfOrNull { it.id } ?: 0
    }

}