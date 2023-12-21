package com.moneo.moneo.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.moneo.moneo.data.local.transaction.Transaction
import com.moneo.moneo.data.remote.response.Perbandingan
import com.moneo.moneo.data.remote.response.PrediksiResponse
import com.moneo.moneo.data.remote.response.RekapResponse
import com.moneo.moneo.data.remote.response.Summary
import com.moneo.moneo.data.remote.retrofit.ApiService
import com.moneo.moneo.data.result.Result
import com.moneo.moneo.datastore.SettingPreferences
import com.moneo.moneo.utils.AppExecutors
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
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