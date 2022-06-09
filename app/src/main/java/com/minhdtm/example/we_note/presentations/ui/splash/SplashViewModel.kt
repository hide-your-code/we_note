package com.minhdtm.example.we_note.presentations.ui.splash

import com.minhdtm.example.we_note.domain.usecase.CheckIsLoginUseCase
import com.minhdtm.example.we_note.presentations.base.BaseViewModel
import com.minhdtm.example.we_note.presentations.base.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkIsLoginUseCase: CheckIsLoginUseCase,
) : BaseViewModel() {
    private val _state = MutableStateFlow(SplashViewState())
    val state: StateFlow<SplashViewState> = _state

    init {
        doSuspend {
            checkIsLoginUseCase().collect { isLogin ->
                _state.update {
                    it.copy(isLogin = isLogin)
                }
            }
        }
    }
}

data class SplashViewState(
    override val isLoading: Boolean = false,
    override val error: Throwable? = null,
    val isLogin: Boolean = false,
) : ViewState(isLoading, error)
