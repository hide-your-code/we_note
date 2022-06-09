package com.minhdtm.example.we_note.presentations

import com.minhdtm.example.we_note.domain.usecase.CheckIsLoginUseCase
import com.minhdtm.example.we_note.domain.usecase.GetUserNameUseCase
import com.minhdtm.example.we_note.domain.usecase.LogoutUserCase
import com.minhdtm.example.we_note.presentations.base.BaseViewModel
import com.minhdtm.example.we_note.presentations.base.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WeNoteViewModel @Inject constructor(
    private val checkIsLoginUseCase: CheckIsLoginUseCase,
    private val logoutUserCase: LogoutUserCase,
    private val getUserNameUseCase: GetUserNameUseCase,
) : BaseViewModel() {
    private val _state = MutableStateFlow(WeNoteViewState())
    val state: StateFlow<WeNoteViewState> = _state

    init {
        doSuspend {
            checkIsLoginUseCase().collect { isLogin ->
                _state.update {
                    it.copy(isLogin = isLogin)
                }
            }
        }

        doSuspend {
            getUserNameUseCase().collect { userName ->
                _state.update {
                    it.copy(userName = userName)
                }
            }
        }
    }

    fun logout() {
        doSuspend {
            logoutUserCase()
        }
    }
}

data class WeNoteViewState(
    override val isLoading: Boolean = false,
    override val error: Throwable? = null,
    val isLogin: Boolean? = null,
    val userName: String = "",
) : ViewState(isLoading, error)
