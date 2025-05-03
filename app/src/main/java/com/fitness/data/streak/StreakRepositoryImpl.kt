package com.fitness.data.streak

import com.fitness.data.auth.AccountService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class StreakRepositoryImpl @Inject constructor(
    db: FirebaseFirestore,
    accountService: AccountService
) : IStreakRepository {

    private val userId = accountService.currentUserId
    private val userDocRef = db.collection("users").document(userId)
    private val dailyStreakDocRef = userDocRef.collection("streaks").document("daily")
    private val weeklyStreakDocRef = userDocRef.collection("streaks").document("weekly")

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun getDailyStreak(): Flow<StreakData> = callbackFlow {
        val listener = dailyStreakDocRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            val streakData = snapshot?.toObject(StreakData::class.java) ?: StreakData()
            trySend(streakData).isSuccess
        }
        awaitClose { listener.remove() }
    }


    override fun getWeeklyStreak(): Flow<StreakData> = callbackFlow {
         val listener = weeklyStreakDocRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            val streakData = snapshot?.toObject(StreakData::class.java) ?: StreakData()
            trySend(streakData).isSuccess
        }
        awaitClose { listener.remove() }
    }

    override suspend fun updateDailyStreak(currentDate: Long) {
        val currentStreakData = dailyStreakDocRef.get().await().toObject(StreakData::class.java) ?: StreakData()
        val lastDate = Calendar.getInstance().apply { timeInMillis = currentStreakData.lastActivityDate }
        val today = Calendar.getInstance().apply { timeInMillis = currentDate }

        val diff = today.timeInMillis - lastDate.timeInMillis
        val daysDiff = TimeUnit.MILLISECONDS.toDays(diff)

        val updatedStreak = when {
            isSameDay(lastDate, today) -> currentStreakData.currentStreak
            daysDiff == 1L -> currentStreakData.currentStreak + 1
            else -> 1
        }

        val newLongest = maxOf(updatedStreak, currentStreakData.longestStreak)
        val newStreakData = currentStreakData.copy(
            currentStreak = updatedStreak,
            longestStreak = newLongest,
            lastActivityDate = currentDate
        )
        dailyStreakDocRef.set(newStreakData).await()
    }

     override suspend fun updateWeeklyStreak(currentDate: Long) {
        val currentStreakData = weeklyStreakDocRef.get().await().toObject(StreakData::class.java) ?: StreakData()
        val lastDate = Calendar.getInstance().apply { timeInMillis = currentStreakData.lastActivityDate }
        val today = Calendar.getInstance().apply { timeInMillis = currentDate }

        val updatedStreak = when {
            isSameWeek(lastDate, today) -> currentStreakData.currentStreak
            isPreviousWeek(lastDate, today) -> currentStreakData.currentStreak + 1
            else -> 1
        }

        val newLongest = maxOf(updatedStreak, currentStreakData.longestStreak)
        val newLastActivityDate = if (updatedStreak > currentStreakData.currentStreak || currentStreakData.lastActivityDate == 0L) {
            currentDate
        } else {
            currentStreakData.lastActivityDate
        }

        val newStreakData = currentStreakData.copy(
            currentStreak = updatedStreak,
            longestStreak = newLongest,
            lastActivityDate = newLastActivityDate
        )
        weeklyStreakDocRef.set(newStreakData).await()
    }

    override suspend fun resetDailyStreak() {
        val currentStreakData = dailyStreakDocRef.get().await().toObject(StreakData::class.java) ?: return
        if (currentStreakData.lastActivityDate == 0L) return

        val lastDate = Calendar.getInstance().apply { timeInMillis = currentStreakData.lastActivityDate }
        val today = Calendar.getInstance()

        if (!isSameDay(lastDate, today) && !isYesterday(lastDate, today)) {
            val resetData = currentStreakData.copy(currentStreak = 0)
            dailyStreakDocRef.set(resetData).await()
        }
    }

    override suspend fun resetWeeklyStreak() {
        val currentStreakData = weeklyStreakDocRef.get().await().toObject(StreakData::class.java) ?: return
        if (currentStreakData.lastActivityDate == 0L) return

        val lastDate = Calendar.getInstance().apply { timeInMillis = currentStreakData.lastActivityDate }
        val today = Calendar.getInstance()

        if (!isSameWeek(lastDate, today) && !isPreviousWeek(lastDate, today)) {
            val resetData = currentStreakData.copy(currentStreak = 0)
            weeklyStreakDocRef.set(resetData).await()
        }
    }

    private fun isYesterday(cal1: Calendar, cal2: Calendar): Boolean {
        val yesterdayCal = cal2.clone() as Calendar
        yesterdayCal.add(Calendar.DAY_OF_YEAR, -1)
        return cal1.get(Calendar.YEAR) == yesterdayCal.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == yesterdayCal.get(Calendar.DAY_OF_YEAR)
    }

    private fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }

    private fun isSameWeek(cal1: Calendar, cal2: Calendar): Boolean {
        cal1.firstDayOfWeek = Calendar.SUNDAY
        cal2.firstDayOfWeek = Calendar.SUNDAY
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)
    }

    private fun isPreviousWeek(cal1: Calendar, cal2: Calendar): Boolean {
        cal1.firstDayOfWeek = Calendar.SUNDAY
        cal2.firstDayOfWeek = Calendar.SUNDAY

        val prevWeekCal = cal2.clone() as Calendar
        prevWeekCal.add(Calendar.WEEK_OF_YEAR, -1)
        return cal1.get(Calendar.YEAR) == prevWeekCal.get(Calendar.YEAR) &&
               cal1.get(Calendar.WEEK_OF_YEAR) == prevWeekCal.get(Calendar.WEEK_OF_YEAR)
    }
}