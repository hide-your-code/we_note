package com.minhdtm.example.we_note.presentations.ui.addnote

import com.minhdtm.example.we_note.domain.usecase.AddNoteUseCase
import com.minhdtm.example.we_note.presentations.base.BaseViewModel
import com.minhdtm.example.we_note.presentations.base.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
) : BaseViewModel() {
    private val _state = MutableStateFlow(AddNoteViewState())
    val state: StateFlow<AddNoteViewState> = _state

    private val _event = Channel<AddNoteEvent>()
    val event = _event.receiveAsFlow()

    fun setTextTitle(title: String) {
        _state.update {
            it.copy(
                title = title,
            )
        }

        checkShowAddButton()
    }

    fun setTextDescription(description: String) {
        _state.update {
            it.copy(
                description = description,
            )
        }

        checkShowAddButton()
    }

    private fun checkShowAddButton() {
        val title = _state.value.title
        val description = _state.value.description

        val buttonState = when {
            title.isBlank() || description.isBlank() -> {
                ButtonAddNoteState.DECLINE
            }
            else -> {
                ButtonAddNoteState.ACCEPT
            }
        }

        _state.update {
            it.copy(buttonAddNoteState = buttonState)
        }
    }

    fun addNote() {
        doSuspend {
            val title = _state.value.title
            val description = _state.value.description

            addNoteUseCase(AddNoteUseCase.Params(title, description)).collect {
                _event.send(AddNoteEvent.BackToPreviousScreen)
            }
        }
    }
}

enum class ButtonAddNoteState {
    DECLINE, ACCEPT,
}

data class AddNoteViewState(
    override val isLoading: Boolean = false,
    override val error: Throwable? = null,
    val buttonAddNoteState: ButtonAddNoteState = ButtonAddNoteState.DECLINE,
    val title: String = "",
    val description: String = "",
) : ViewState(isLoading, error)

sealed class AddNoteEvent {
    object BackToPreviousScreen : AddNoteEvent()
}
