package com.minhdtm.example.we_note.domain.usecase

import com.minhdtm.example.we_note.domain.repositories.UserRepository
import com.minhdtm.example.we_note.domain.usecase.base.SuspendUseCase
import com.minhdtm.example.we_note.presentations.di.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUseCase @Inject constructor(
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository,
) : SuspendUseCase<LoginUseCase.Params, Unit>(mainDispatcher) {
    override suspend fun execute(params: Params?) {
        if (params == null || params.userName.isBlank()) {
            throw Throwable(message = "Username must be non null!")
        } else {
            userRepository.login(params.userName)
        }
    }

    data class Params(
        val userName: String
    )
}
