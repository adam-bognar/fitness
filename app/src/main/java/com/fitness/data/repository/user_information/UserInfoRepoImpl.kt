package com.fitness.data.repository.user_information

import com.fitness.data.auth.AccountService
import com.fitness.model.macros.UserInformation
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserInfoRepoImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val accountService: AccountService
): IUserInformationRepository {
    private val user = accountService.currentUserId

    private val collection = db.collection("users").document(user).collection("user_information")

    private val userFlow = MutableStateFlow<UserInformation>(UserInformation())

    init {
        collection.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val userInformation = snapshot.documents.mapNotNull { it.toObject(UserInformation::class.java) }
                userFlow.value = userInformation.firstOrNull() ?: UserInformation()
            }
        }
    }

    override fun getUserInformation(): Flow<UserInformation> = userFlow.asStateFlow()

    override suspend fun upsert(userInformation: UserInformation) {
        collection.document("measurements").set(userInformation).addOnSuccessListener {
            userFlow.value = userInformation
        }.await()
    }

    override suspend fun delete(userInformation: UserInformation) {
        collection.document("measurements").delete().await()
    }


}