package com.igordudka.aichat.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {

    private companion object{
        val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
        val AS_SYSTEM = booleanPreferencesKey("as_system")
        val IS_LOVE = booleanPreferencesKey("is_love")
        val THEME = intPreferencesKey("Theme")
    }


    suspend fun saveThemePreference(isDark: Boolean){
        dataStore.edit {
            it[IS_DARK_THEME] = isDark
        }
    }
    suspend fun saveSystemPreference(asSystem: Boolean){
        dataStore.edit {
            it[AS_SYSTEM] = asSystem
        }
    }
    suspend fun saveLovePreference(isLove: Boolean){
        dataStore.edit {
            it[IS_LOVE] = isLove
        }
    }
    suspend fun changeTheme(value: Int){
        dataStore.edit {
            it[THEME] = value
        }
    }
    val isDarkTheme: Flow<Boolean> = dataStore.data.map {
        it[IS_DARK_THEME] ?: false
    }
    val isLove: Flow<Boolean> = dataStore.data.map {
        it[IS_LOVE] ?: true
    }
    val asSystem: Flow<Boolean> = dataStore.data.map {
        it[AS_SYSTEM] ?: true
    }
    val theme: Flow<Int> = dataStore.data.map {
        it[THEME] ?: 0
    }

}