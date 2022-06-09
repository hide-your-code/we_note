package com.minhdtm.example.we_note.domain.repositories

import com.minhdtm.example.we_note.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun login(userName: String)

    fun isLogin(): Flow<Boolean>

    fun getUserName(): Flow<String>

    suspend fun logout()

    fun getAllUsers(): Flow<List<User>>
}
