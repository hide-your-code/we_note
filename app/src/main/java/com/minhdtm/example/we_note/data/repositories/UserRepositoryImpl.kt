package com.minhdtm.example.we_note.data.repositories

import com.minhdtm.example.we_note.data.local.PreferenceStorage
import com.minhdtm.example.we_note.data.model.User
import com.minhdtm.example.we_note.data.remote.FireStoreHelper
import com.minhdtm.example.we_note.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: FireStoreHelper,
    private val preference: PreferenceStorage,
) : UserRepository {
    override suspend fun login(userName: String) {
        api.register(userName).collect {
            if (it) {
                preference.saveUserName(userName)
            }
        }
    }

    override fun isLogin(): Flow<Boolean> = preference.getUserName.map {
        it.isNotBlank()
    }

    override fun getUserName(): Flow<String> = preference.getUserName

    override suspend fun logout() {
        preference.saveUserName("")
    }

    override fun getAllUsers(): Flow<List<User>> = api.getAllUser()
}
