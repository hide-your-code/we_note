package com.minhdtm.example.we_note.domain.usecase

import com.minhdtm.example.we_note.domain.repositories.NoteRepository
import com.minhdtm.example.we_note.domain.usecase.base.FlowUseCase
import com.minhdtm.example.we_note.presentations.di.IoDispatcher
import com.minhdtm.example.we_note.presentations.utils.asFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateNoteUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val noteRepository: NoteRepository,
) : FlowUseCase<UpdateNoteUseCase.Params, Unit>(ioDispatcher) {
    override fun execute(params: Params?): Flow<Unit> = if (params == null) {
        Throwable(message = "Note must be non null!").asFlow()
    } else {
        noteRepository.updateNote(params.id, params.title, params.description)
    }

    data class Params(
        val id: String,
        val title: String,
        val description: String,
    )
}
