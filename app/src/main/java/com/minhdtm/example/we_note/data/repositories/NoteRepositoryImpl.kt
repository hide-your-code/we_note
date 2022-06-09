package com.minhdtm.example.we_note.data.repositories

import com.minhdtm.example.we_note.data.local.PreferenceStorage
import com.minhdtm.example.we_note.data.model.Note
import com.minhdtm.example.we_note.data.remote.FireStoreHelper
import com.minhdtm.example.we_note.domain.repositories.NoteRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import javax.inject.Inject

@OptIn(FlowPreview::class)
class NoteRepositoryImpl @Inject constructor(
    private val api: FireStoreHelper,
    private val preference: PreferenceStorage,
) : NoteRepository {
    override fun addNote(title: String, description: String): Flow<Unit> = preference.getUserName.flatMapConcat {
        api.addNote(it, title, description)
    }

    override fun getYourNote(): Flow<List<Note>> = preference.getUserName.flatMapConcat {
        api.getNotes(it)
    }

    override fun getNote(id: String): Flow<Note> = preference.getUserName.flatMapConcat {
        api.getNote(it, id)
    }

    override fun getNotes(userName: String): Flow<List<Note>> = api.getNotes(userName)

    override fun updateNote(id: String, title: String, description: String): Flow<Unit> =
        preference.getUserName.flatMapConcat {
            api.updateNote(it, id, title, description)
        }

    override fun deleteNote(id: String): Flow<Unit> = preference.getUserName.flatMapConcat {
        api.deleteNote(it, id)
    }
}
