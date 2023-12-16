package com.moneo.moneo.di

import android.content.Context
import com.moneo.moneo.data.remote.retrofit.ApiConfig
import com.moneo.moneo.data.repository.RekapRepository
import com.moneo.moneo.data.repository.RekeningRepository

object RekeningInjection {

    fun provideRepository(context: Context): RekeningRepository {
        val apiService = ApiConfig.getApiService()

        return RekeningRepository(apiService)
    }

}