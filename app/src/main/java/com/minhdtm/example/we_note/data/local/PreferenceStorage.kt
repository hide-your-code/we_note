package com.minhdtm.example.we_note.data.local

import kotlinx.coroutines.flow.Flow

interface PreferenceStorage {
    suspend fun saveUserName(userName: String)
    val getUserName: Flow<String>
}
