package com.moneo.moneo.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.moneo.moneo.data.remote.retrofit.PrediksiConfig
import com.moneo.moneo.data.repository.PrediksiRepository
import com.moneo.moneo.datastore.SettingPreferences

object PrediksiInjection {

    @RequiresApi(Build.VERSION_CODES.N)
    fun provideRepository(context: Context): PrediksiRepository {
        val dataStore = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            PreferenceDataStoreFactory.create {
                context.dataDir.resolve("settings.preferences_pb")
            }
        } else {
            throw UnsupportedOperationException("Unsupported Android version")
        }

        val apiService = PrediksiConfig.getApiServicePrediksi()
        val preferences = SettingPreferences.getInstance(dataStore)

        return PrediksiRepository(preferences, apiService)
    }
}