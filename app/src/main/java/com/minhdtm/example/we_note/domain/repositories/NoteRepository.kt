package com.minhdtm.example.we_note.domain.repositories

import com.minhdtm.example.we_note.data.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun addNote(title: String, description: String): Flow<Unit>

    fun getNote(id: String): Flow<Note>

    fun getNotes(userName: String): Flow<List<Note>>

    fun deleteNote(id: String): Flow<Unit>

    fun updateNote(id: String, title: String, description: String): Flow<Unit>

    fun getYourNote(): Flow<List<Note>>
}
