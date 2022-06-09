package com.minhdtm.example.we_note.domain.usecase

import com.minhdtm.example.we_note.data.model.Note
import com.minhdtm.example.we_note.domain.repositories.NoteRepository
import com.minhdtm.example.we_note.domain.usecase.base.FlowUseCase
import com.minhdtm.example.we_note.presentations.di.MainDispatcher
import com.minhdtm.example.we_note.presentations.utils.asFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetNotesByUserNameUseCase @Inject constructor(
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val noteRepository: NoteRepository,
) : FlowUseCase<GetNotesByUserNameUseCase.Params, List<Note>>(mainDispatcher) {
    override fun execute(params: Params?): Flow<List<Note>> = if (params == null) {
        Throwable(message = "User name must be non null!").asFlow()
    } else {
        noteRepository.getNotes(params.userName)
    }

    data class Params(
        val userName: String
    )
}