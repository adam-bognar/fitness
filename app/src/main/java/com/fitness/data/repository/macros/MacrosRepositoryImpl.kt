package com.fitness.data.repository.macros

import com.fitness.data.auth.AccountService
import com.fitness.model.macros.DailyMacroRecord
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MacrosRepositoryImpl @Inject constructor(
    db: FirebaseFirestore,
    accountService: AccountService
): IMacrosRepository {

    private val user = accountService.currentUserId

    private val collection = db.collection("users").document(user).collection("macros")

    private val _macrosFlow = MutableStateFlow<List<DailyMacroRecord>>(emptyList())

    init {
        collection.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val macros = snapshot.documents.mapNotNull { it.toObject(DailyMacroRecord::class.java) }
                _macrosFlow.value = macros
            }
        }
    }

    override fun getMacros(): Flow<List<DailyMacroRecord>> = _macrosFlow.asStateFlow()


    override suspend fun upsert(macro: DailyMacroRecord) {

        collection.document(macro.id.toString()).set(macro).addOnSuccessListener {
            _macrosFlow.value = _macrosFlow.value.map { if (it.id == macro.id) macro else it }
        }
    }

    override suspend fun delete(macro: DailyMacroRecord) {
        collection.document(macro.date.toString()).delete().await()
    }



}