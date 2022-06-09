package com.minhdtm.example.we_note.presentations.ui.anotherusers

import com.minhdtm.example.we_note.data.model.User
import com.minhdtm.example.we_note.domain.usecase.GetAllUsersUseCase
import com.minhdtm.example.we_note.domain.usecase.GetUserNameUseCase
import com.minhdtm.example.we_note.presentations.base.BaseViewModel
import com.minhdtm.example.we_note.presentations.base.ViewState
import com.minhdtm.example.we_note.presentations.model.UserViewData
import com.minhdtm.example.we_note.presentations.model.UserViewDataMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AnotherUsersViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val getUserNameUseCase: GetUserNameUseCase,
    private val userViewDataMapper: UserViewDataMapper,
) : BaseViewModel() {
    private val _state = MutableStateFlow(AnotherUsersViewState())
    val state: StateFlow<AnotherUsersViewState> = _state

    init {
        doSuspend {
            getAllUsersUseCase().collect {
                showListUser(it)
            }
        }
    }

    private fun showListUser(listUser: List<User>) {
        doSuspend {
            getUserNameUseCase().collect { userName ->
                val listUserViewData =
                    listUser.filter { it.name != userName }.map { userViewDataMapper.mapToViewData(it) }

                _state.update {
                    it.copy(listUser = listUserViewData)
                }
            }
        }
    }
}

data class AnotherUsersViewState(
    override val isLoading: Boolean = false,
    override val error: Throwable? = null,
    val listUser: List<UserViewData> = emptyList(),
) : ViewState(isLoading, error)
