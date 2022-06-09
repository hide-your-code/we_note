package com.minhdtm.example.we_note.data.di

import com.minhdtm.example.we_note.data.local.PreferenceStorage
import com.minhdtm.example.we_note.data.remote.FireStoreHelper
import com.minhdtm.example.we_note.data.repositories.NoteRepositoryImpl
import com.minhdtm.example.we_note.data.repositories.UserRepositoryImpl
import com.minhdtm.example.we_note.domain.repositories.NoteRepository
import com.minhdtm.example.we_note.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideUserRepository(
        server: FireStoreHelper,
        preference: PreferenceStorage,
    ): UserRepository = UserRepositoryImpl(server, preference)

    @Provides
    fun provideNoteRepository(
        server: FireStoreHelper,
        preference: PreferenceStorage,
    ): NoteRepository = NoteRepositoryImpl(server, preference)
}
