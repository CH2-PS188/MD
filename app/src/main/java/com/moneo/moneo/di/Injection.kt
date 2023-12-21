package com.moneo.moneo.di

import android.content.Context
import android.os.Build
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.moneo.moneo.data.local.rekap.Rekap
import com.moneo.moneo.data.local.rekap.RekapDatabase
import com.moneo.moneo.data.local.rekening.RekeningDatabase
import com.moneo.moneo.data.local.transaction.TransactionDatabase
import com.moneo.moneo.data.remote.retrofit.ApiConfig
import com.moneo.moneo.data.remote.retrofit.ApiModelConfig
import com.moneo.moneo.data.remote.retrofit.ApiService
import com.moneo.moneo.data.repository.RekapRepository
import com.moneo.moneo.data.repository.RekeningRepository
import com.moneo.moneo.data.repository.SettingRepository
import com.moneo.moneo.data.repository.TransactionRepository
import com.moneo.moneo.datastore.SettingPreferences
import com.moneo.moneo.utils.AppExecutors

object Injection {
    fun provideTransactionRepository(context: Context): TransactionRepository {
        val transactionDatabase = TransactionDatabase.getDatabase(context)
        val transactionDao = transactionDatabase.transactionDao()
        val rekeningDatabase = RekeningDatabase.getDatabase(context)
        val rekeningDao = rekeningDatabase.rekeningDao()
        val apiService = ApiConfig.getApiService()
        val appExecutors = AppExecutors()
        return TransactionRepository.getInstance(transactionDao, rekeningDao, apiService, appExecutors)
    }

    fun provideAccountRepository(context: Context): RekeningRepository {
        val database = RekeningDatabase.getDatabase(context)
        val dao = database.rekeningDao()
        val apiService = ApiConfig.getApiService()
        val appExecutors = AppExecutors()
        return RekeningRepository.getInstance(dao, apiService, appExecutors)
    }

    fun provideRekapRepository(context: Context): RekapRepository {
//        val database = RekapDatabase.getDatabase(context)
//        val dao = database.rekapDao()
        val apiService = ApiConfig.getApiService()
        val apiModelService = ApiModelConfig.getApiService()
        val appExecutors = AppExecutors()
        return RekapRepository.getInstance(apiService, apiModelService, appExecutors)
    }

    fun provideSettingRepository(context: Context): SettingRepository {
        val dataStore = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            PreferenceDataStoreFactory.create {
                context.dataDir.resolve("settings.preferences_pb")
            }
        } else {
            throw UnsupportedOperationException("Unsupported Android version")
        }
        val apiService = ApiConfig.getApiService()
        val preferences = SettingPreferences.getInstance(dataStore)
        val appExecutors = AppExecutors()
        return SettingRepository.getInstance(preferences, apiService, appExecutors)
    }
}