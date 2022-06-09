package com.minhdtm.example.we_note.domain.usecase

import com.minhdtm.example.we_note.data.model.Note
import com.minhdtm.example.we_note.domain.repositories.NoteRepository
import com.minhdtm.example.we_note.domain.usecase.base.FlowUseCase
import com.minhdtm.example.we_note.presentations.di.IoDispatcher
import com.minhdtm.example.we_note.presentations.utils.asFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetNoteByIdUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val noteRepository: NoteRepository,
) : FlowUseCase<GetNoteByIdUseCase.Params, Note>(ioDispatcher) {
    override fun execute(params: Params?): Flow<Note> = if (params == null) {
        Throwable(message = "Note id must be non null!").asFlow()
    } else {
        noteRepository.getNote(params.id)
    }

    data class Params(
        val id: String,
    )
}
