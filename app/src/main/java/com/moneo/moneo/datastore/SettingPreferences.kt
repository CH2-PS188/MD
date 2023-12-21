package com.moneo.moneo.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val NOTIFICATION_KEY = booleanPreferencesKey("notification_enabled")
    private val THEME_KEY = booleanPreferencesKey("theme_setting")

    //TODO: Thema
    fun getThemeSetting(): Flow<Boolean>{
        return dataStore.data.map {
            it[THEME_KEY]?: false
        }
    }

    suspend fun saveThemeSetting(isLightMode: Boolean){
        dataStore.edit {
            it[THEME_KEY] = isLightMode
        }
    }

    //TODO: Notification
    fun getNotifikasi(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[NOTIFICATION_KEY] ?: false
        }
    }

    suspend fun setNotificationEnabled(enabled: Boolean){
        dataStore.edit { preferenes ->
            preferenes[NOTIFICATION_KEY] = enabled
        }
    }


    companion object{
        @Volatile
        private var instance: SettingPreferences? = null
        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences =
            instance ?: synchronized(this){
                instance ?: SettingPreferences(dataStore)
            }
    }
}