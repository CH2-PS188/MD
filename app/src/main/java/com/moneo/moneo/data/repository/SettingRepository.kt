package com.moneo.moneo.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.moneo.moneo.data.remote.response.PrediksiResponse
import com.moneo.moneo.data.remote.retrofit.ApiService
import com.moneo.moneo.datastore.SettingPreferences
import com.moneo.moneo.utils.AppExecutors
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Response

class SettingRepository(
    private val preferences: SettingPreferences,
    private val apiService: ApiService,
    private val appExecutors: AppExecutors
) {

    fun getThemeSetting(): LiveData<Boolean> {
        return preferences.getThemeSetting().asLiveData()
    }

    suspend fun saveThemeSetting(isLightTheme: Boolean){
        preferences.saveThemeSetting(isLightTheme)
    }

    suspend fun getPrediksi(token: String, idAccount: String): Response<PrediksiResponse> {
        return apiService.getPrediksi(token, idAccount)
    }

    suspend fun isNotificationEnabled(): Boolean {
        return preferences.isNotificationEnabled().first()
    }

    suspend fun setNotificationEnabled(enabled: Boolean) {
        preferences.setNotificationEnabled(enabled)
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingRepository? = null
        fun getInstance(
            preferences: SettingPreferences,
            apiService: ApiService,
            appExecutors: AppExecutors
        ): SettingRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: SettingRepository(preferences, apiService, appExecutors)
            }.also { INSTANCE = it }
    }
}