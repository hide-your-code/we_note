package com.minhdtm.example.we_note.presentations.ui.register

import com.minhdtm.example.we_note.domain.usecase.LoginUseCase
import com.minhdtm.example.we_note.presentations.base.BaseViewModel
import com.minhdtm.example.we_note.presentations.base.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val loginUserCase: LoginUseCase,
) : BaseViewModel() {
    private val _state = MutableStateFlow(RegisterViewState())
    val state: StateFlow<RegisterViewState> = _state

    private val _event = Channel<RegisterEvent>()
    val event = _event.receiveAsFlow()

    fun login(userName: String) {
        doSuspend {
            _state.update {
                it.copy(buttonLogin = ButtonLoginState.LOADING)
            }

            try {
                loginUserCase(LoginUseCase.Params(userName.trim()))
                _state.update {
                    it.copy(buttonLogin = ButtonLoginState.DONE)
                }
                delay(1000L)
                _event.send(RegisterEvent.NavigateToHome)
            } catch (e: Throwable) {
                _state.update {
                    it.copy(buttonLogin = ButtonLoginState.ERROR)
                }
                throw e
            }
        }
    }

    override fun showError(error: Throwable) {
        super.showError(error)
        _state.update {
            it.copy(
                error = error
            )
        }
    }

    override fun hideError() {
        super.hideError()
        _state.update {
            it.copy(
                error = null
            )
        }
    }
}

enum class ButtonLoginState {
    NORMAL, LOADING, ERROR, DONE
}

data class RegisterViewState(
    override val isLoading: Boolean = false,
    override val error: Throwable? = null,
    val buttonLogin: ButtonLoginState = ButtonLoginState.NORMAL,
) : ViewState(isLoading, error)

sealed class RegisterEvent {
    object NavigateToHome : RegisterEvent()
}
