package com.minhdtm.example.we_note.presentations.ui.editnote

import androidx.lifecycle.SavedStateHandle
import com.minhdtm.example.we_note.domain.usecase.GetNoteByIdUseCase
import com.minhdtm.example.we_note.domain.usecase.UpdateNoteUseCase
import com.minhdtm.example.we_note.presentations.base.BaseViewModel
import com.minhdtm.example.we_note.presentations.base.ViewState
import com.minhdtm.example.we_note.presentations.model.NoteViewDataMapper
import com.minhdtm.example.we_note.presentations.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val noteViewDataMapper: NoteViewDataMapper,
) : BaseViewModel() {
    private val _state = MutableStateFlow(EditNoteViewState())
    val state: StateFlow<EditNoteViewState> = _state

    private val _event = Channel<EditNoteEvent>()
    val event = _event.receiveAsFlow()

    private var id = ""
    private var titleDefault = ""
    private var descriptionDefault = ""

    init {
        doSuspend {
            id = savedStateHandle.get<String>(Constants.Keys.NOTE_ID) ?: ""
            if (id.isNotBlank()) {
                getNoteByIdUseCase(GetNoteByIdUseCase.Params(id)).collect { note ->
                    val noteViewData = noteViewDataMapper.mapToViewData(note)

                    titleDefault = noteViewData.title
                    descriptionDefault = noteViewData.description

                    _state.update {
                        it.copy(
                            title = noteViewData.title,
                            description = noteViewData.description,
                        )
                    }
                }
            }
        }
    }

    fun setTextTitle(title: String) {
        _state.update {
            it.copy(
                title = title,
            )
        }

        checkShowEditButton()
    }

    fun setTextDescription(description: String) {
        _state.update {
            it.copy(
                description = description,
            )
        }

        checkShowEditButton()
    }

    private fun checkShowEditButton() {
        val title = _state.value.title
        val description = _state.value.description

        val buttonState = when {
            title.isBlank() || description.isBlank() -> {
                ButtonEditNoteState.DECLINE
            }
            title == titleDefault && description == descriptionDefault -> {
                ButtonEditNoteState.DECLINE
            }
            else -> {
                ButtonEditNoteState.ACCEPT
            }
        }

        _state.update {
            it.copy(buttonEditNoteState = buttonState)
        }
    }

    fun updateNote() {
        doSuspend {
            val title = _state.value.title
            val description = _state.value.description

            updateNoteUseCase(UpdateNoteUseCase.Params(id, title, description)).collect {
                _event.send(EditNoteEvent.BackToPreviousScreen)
            }
        }
    }
}

enum class ButtonEditNoteState {
    DECLINE, ACCEPT,
}

data class EditNoteViewState(
    override val isLoading: Boolean = false,
    override val error: Throwable? = null,
    val buttonEditNoteState: ButtonEditNoteState = ButtonEditNoteState.DECLINE,
    val title: String = "",
    val description: String = "",
) : ViewState()

sealed class EditNoteEvent {
    object BackToPreviousScreen : EditNoteEvent()
}
