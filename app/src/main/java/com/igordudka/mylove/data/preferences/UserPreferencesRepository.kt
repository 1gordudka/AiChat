package com.igordudka.mylove.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {

    private companion object{
        val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
        val IS_LOVE = booleanPreferencesKey("is_love")
    }


    suspend fun saveThemePreference(isDark: Boolean){
        dataStore.edit {
            it[IS_DARK_THEME] = isDark
        }
    }
    suspend fun saveLovePreference(isLove: Boolean){
        dataStore.edit {
            it[IS_LOVE] = isLove
        }
    }
    val isDarkTheme: Flow<Boolean> = dataStore.data.map {
        it[IS_DARK_THEME] ?: false
    }
    val isLove: Flow<Boolean> = dataStore.data.map {
        it[IS_LOVE] ?: true
    }

}