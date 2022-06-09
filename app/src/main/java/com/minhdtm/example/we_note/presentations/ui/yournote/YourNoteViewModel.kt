package com.minhdtm.example.we_note.presentations.ui.yournote

import com.minhdtm.example.we_note.domain.usecase.DeleteNoteUseCase
import com.minhdtm.example.we_note.domain.usecase.GetYourNoteUseCase
import com.minhdtm.example.we_note.presentations.base.BaseViewModel
import com.minhdtm.example.we_note.presentations.base.ViewState
import com.minhdtm.example.we_note.presentations.model.NoteViewData
import com.minhdtm.example.we_note.presentations.model.NoteViewDataMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class YourNoteViewModel @Inject constructor(
    private val getYourNoteUseCase: GetYourNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val noteViewDataMapper: NoteViewDataMapper,
) : BaseViewModel() {
    private val _state = MutableStateFlow(YourNoteViewState())
    val state: StateFlow<YourNoteViewState> = _state

    init {
        doSuspend {
            getYourNoteUseCase().collect { notes ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        listNote = notes.map { note -> noteViewDataMapper.mapToViewData(note) }
                            .sortedByDescending { note -> note.timeStamp },
                    )
                }
            }
        }
    }

    fun deleteNote(id: String) {
        doSuspend {
            deleteNoteUseCase(DeleteNoteUseCase.Params(id)).collect {
                // Do nothing
            }
        }
    }
}

data class YourNoteViewState(
    override val isLoading: Boolean = true,
    override val error: Throwable? = null,
    val listNote: List<NoteViewData> = emptyList(),
) : ViewState(isLoading, error)
