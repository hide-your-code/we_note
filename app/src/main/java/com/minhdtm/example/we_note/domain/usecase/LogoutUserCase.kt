package com.minhdtm.example.we_note.domain.usecase

import com.minhdtm.example.we_note.domain.repositories.UserRepository
import com.minhdtm.example.we_note.domain.usecase.base.SuspendUseCase
import com.minhdtm.example.we_note.presentations.di.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogoutUserCase @Inject constructor(
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository,
) : SuspendUseCase<Unit, Unit>(mainDispatcher) {
    override suspend fun execute(params: Unit?) = userRepository.logout()
}
