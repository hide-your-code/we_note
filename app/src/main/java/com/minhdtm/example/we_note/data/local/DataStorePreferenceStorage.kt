package com.minhdtm.example.we_note.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStorePreferenceStorage @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferenceStorage {
    override suspend fun saveUserName(userName: String) {
        dataStore.setValue {
            it[PreferenceKeys.USER_NAME] = userName
        }
    }

    override val getUserName: Flow<String> = dataStore.getValue {
        it[PreferenceKeys.USER_NAME] ?: ""
    }

    object PreferenceKeys {
        val USER_NAME = stringPreferencesKey("USER_NAME")
    }

    companion object {
        const val PREFS_NAME = "we_note"
    }
}
