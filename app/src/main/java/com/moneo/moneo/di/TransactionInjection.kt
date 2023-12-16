package com.moneo.moneo.di

import android.content.Context
import com.moneo.moneo.data.remote.retrofit.ApiConfig
import com.moneo.moneo.data.repository.TransactionRepository

object TransactionInjection {
    fun provideRepository(context: Context): TransactionRepository {
        val apiService = ApiConfig.getApiService()

        return TransactionRepository(apiService)
    }
}