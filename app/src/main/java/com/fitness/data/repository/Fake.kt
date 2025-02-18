
import com.fitness.data.repository.macros.IMacrosRepository
import com.fitness.data.repository.user_information.IUserInformationRepository
import com.fitness.model.macros.DailyMacroRecord
import com.fitness.model.macros.UserInformation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

// Fake implementation of IUserInformationRepository
class FakeUserInformationRepository : IUserInformationRepository {
    private val userInformationFlow = MutableStateFlow(UserInformation())

    override fun getUserInformation(): Flow<UserInformation> = userInformationFlow.asStateFlow()

    override suspend fun upsert(userInformation: UserInformation) {
        userInformationFlow.value = userInformation
    }

    override suspend fun delete(userInformation: UserInformation) {
        userInformationFlow.value = UserInformation()
    }
}

// Fake implementation of IMacrosRepository
class FakeMacrosRepository : IMacrosRepository {
    private val macrosFlow = MutableStateFlow<List<DailyMacroRecord>>(emptyList())

    override fun getMacros(): Flow<List<DailyMacroRecord>> = macrosFlow.asStateFlow()


    override suspend fun upsert(macro: DailyMacroRecord) {
        macrosFlow.value = macrosFlow.value.toMutableList().apply {
            removeIf { it.date == macro.date }
            add(macro)
        }
    }

    override suspend fun delete(macro: DailyMacroRecord) {
        macrosFlow.value = macrosFlow.value.filterNot { it.date == macro.date }
    }
}