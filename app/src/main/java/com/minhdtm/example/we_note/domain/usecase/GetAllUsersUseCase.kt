package com.minhdtm.example.we_note.domain.usecase

import com.minhdtm.example.we_note.data.model.User
import com.minhdtm.example.we_note.domain.repositories.UserRepository
import com.minhdtm.example.we_note.domain.usecase.base.FlowUseCase
import com.minhdtm.example.we_note.presentations.di.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllUsersUseCase @Inject constructor(
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository,
) : FlowUseCase<Unit, List<User>>(mainDispatcher) {
    override fun execute(params: Unit?): Flow<List<User>> = userRepository.getAllUsers()
}
