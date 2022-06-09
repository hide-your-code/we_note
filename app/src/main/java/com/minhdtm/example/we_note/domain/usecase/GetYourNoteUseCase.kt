package com.minhdtm.example.we_note.domain.usecase

import com.minhdtm.example.we_note.data.model.Note
import com.minhdtm.example.we_note.domain.repositories.NoteRepository
import com.minhdtm.example.we_note.domain.usecase.base.FlowUseCase
import com.minhdtm.example.we_note.presentations.di.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetYourNoteUseCase @Inject constructor(
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val noteRepository: NoteRepository,
) : FlowUseCase<Unit, List<Note>>(mainDispatcher) {
    override fun execute(params: Unit?): Flow<List<Note>> = noteRepository.getYourNote()
}
