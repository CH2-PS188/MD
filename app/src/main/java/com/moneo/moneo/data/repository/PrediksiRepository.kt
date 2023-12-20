package com.moneo.moneo.data.repository

import com.moneo.moneo.data.remote.response.PrediksiResponse
import com.moneo.moneo.data.remote.retrofit.ApiService
import com.moneo.moneo.datastore.SettingPreferences
import kotlinx.coroutines.flow.first
import retrofit2.Response

class PrediksiRepository(private val preferences: SettingPreferences, private val apiService: ApiService) {

    suspend fun getPrediksi(token: String, idAccount: String): Response<PrediksiResponse> {
        return apiService.getPrediksi(token, idAccount)
    }

    suspend fun isNotificationEnabled(): Boolean {
        return preferences.isNotificationEnabled().first()
    }

    suspend fun setNotificationEnabled(enabled: Boolean) {
        preferences.setNotificationEnabled(enabled)
    }
}