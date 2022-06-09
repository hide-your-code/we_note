package com.minhdtm.example.we_note.domain.usecase

import com.minhdtm.example.we_note.domain.repositories.UserRepository
import com.minhdtm.example.we_note.domain.usecase.base.FlowUseCase
import com.minhdtm.example.we_note.presentations.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserNameUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository,
) : FlowUseCase<Unit, String>(ioDispatcher) {
    override fun execute(params: Unit?): Flow<String> = userRepository.getUserName()
}
