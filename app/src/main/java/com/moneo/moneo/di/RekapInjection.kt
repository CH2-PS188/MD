package com.moneo.moneo.di


import android.content.Context
import com.moneo.moneo.data.remote.retrofit.ApiConfig
import com.moneo.moneo.data.repository.RekapRepository

object RekapInjection {

    fun provideRepository(context: Context): RekapRepository {
        val apiService = ApiConfig.getApiService()

        return RekapRepository(apiService)
    }
}