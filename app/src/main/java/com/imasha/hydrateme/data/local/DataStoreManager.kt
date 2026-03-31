package com.imasha.hydrateme.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.imasha.hydrateme.utils.AppConstants
import kotlinx.coroutines.flow.first
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(name = "hydrate_me_prefs")

class DataStoreManager @Inject constructor (private val context: Context) {

    private object PrefKeys {
        val APP_ENTRY = booleanPreferencesKey(AppConstants.APP_ENTRY)
    }

    suspend fun saveAppEntry() {
        context.dataStore.edit {
            it[PrefKeys.APP_ENTRY] = true
        }
    }

    suspend fun readAppEntry(): Boolean {
        val preferences = context.dataStore.data.first()
        return preferences[PrefKeys.APP_ENTRY] ?: false
    }

    /*fun readAppEntry(): Flow<Boolean> {
        return context.dataStore.data.map {
            it[PrefKeys.APP_ENTRY] ?: false
        }
    }*/
}