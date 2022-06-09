package com.minhdtm.example.we_note.data.remote

import com.minhdtm.example.we_note.data.model.Note
import com.minhdtm.example.we_note.data.model.User
import kotlinx.coroutines.flow.Flow

interface FireStoreHelper {
    fun register(userName: String): Flow<Boolean>

    fun getAllUser(): Flow<List<User>>

    fun addNote(userName: String, title: String, description: String): Flow<Unit>

    fun getNotes(userName: String): Flow<List<Note>>

    fun getNote(userName: String, id: String): Flow<Note>

    fun updateNote(userName: String, id: String, title: String, description: String): Flow<Unit>

    fun deleteNote(userName: String, id: String): Flow<Unit>
}
