package com.minhdtm.example.we_note.domain.usecase

import com.minhdtm.example.we_note.domain.repositories.NoteRepository
import com.minhdtm.example.we_note.domain.usecase.base.FlowUseCase
import com.minhdtm.example.we_note.presentations.di.MainDispatcher
import com.minhdtm.example.we_note.presentations.utils.asFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddNoteUseCase @Inject constructor(
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val noteRepository: NoteRepository,
) : FlowUseCase<AddNoteUseCase.Params, Unit>(mainDispatcher) {
    override fun execute(params: Params?): Flow<Unit> = if (params == null) {
        Throwable(message = "Note must be non null!").asFlow()
    } else {
        noteRepository.addNote(params.title, params.description)
    }

    data class Params(
        val title: String,
        val description: String,
    )
}
