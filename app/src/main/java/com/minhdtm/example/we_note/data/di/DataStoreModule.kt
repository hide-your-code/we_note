package com.minhdtm.example.we_note.data.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.minhdtm.example.we_note.data.local.DataStorePreferenceStorage
import com.minhdtm.example.we_note.data.local.PreferenceStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    private val Context.dataStore by preferencesDataStore(DataStorePreferenceStorage.PREFS_NAME)

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): PreferenceStorage =
        DataStorePreferenceStorage(context.dataStore)
}
