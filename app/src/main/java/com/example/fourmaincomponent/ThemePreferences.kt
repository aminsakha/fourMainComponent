package com.example.fourmaincomponent

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class ThemePreferences(private val context: Context) {

    private val Context.dataStore by preferencesDataStore(name = "settings")

    private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme")

    suspend fun saveDarkThemeEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[DARK_THEME_KEY] = enabled
        }
    }

    suspend fun isDarkThemeEnabled(): Boolean {
        val prefs = context.dataStore.data.first()
        return prefs[DARK_THEME_KEY] ?: false
    }
}