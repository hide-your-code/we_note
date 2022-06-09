package com.minhdtm.example.we_note.presentations.ui.showusernote

import androidx.lifecycle.SavedStateHandle
import com.minhdtm.example.we_note.domain.usecase.GetNotesByUserNameUseCase
import com.minhdtm.example.we_note.presentations.base.BaseViewModel
import com.minhdtm.example.we_note.presentations.base.ViewState
import com.minhdtm.example.we_note.presentations.model.NoteViewData
import com.minhdtm.example.we_note.presentations.model.NoteViewDataMapper
import com.minhdtm.example.we_note.presentations.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ShowUserNoteViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getNotesByUserNameUseCase: GetNotesByUserNameUseCase,
    private val noteViewDataMapper: NoteViewDataMapper,
) : BaseViewModel() {
    private val _state = MutableStateFlow(ShowUserNoteViewState())
    val state: StateFlow<ShowUserNoteViewState> = _state

    init {
        doSuspend {
            val userName = savedStateHandle[Constants.Keys.USER_NAME] ?: ""
            getNotesByUserNameUseCase(GetNotesByUserNameUseCase.Params(userName)).collect { notes ->
                _state.update {
                    it.copy(
                        userName = userName,
                        listNote = notes.map { note -> noteViewDataMapper.mapToViewData(note) },
                    )
                }
            }
        }
    }
}

data class ShowUserNoteViewState(
    override val isLoading: Boolean = false,
    override val error: Throwable? = null,
    val userName: String = "",
    val listNote: List<NoteViewData> = emptyList(),
) : ViewState(isLoading, error)
