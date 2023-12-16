package com.moneo.moneo.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.moneo.moneo.data.remote.response.RekapResponse
import com.moneo.moneo.data.remote.retrofit.ApiService
import com.moneo.moneo.data.result.Result
import retrofit2.Response

class RekapRepository (private val apiService: ApiService){
    suspend fun getAllLaporan(token: String, idAccount: String): Response<RekapResponse> {
        return apiService.getAllLaporan(token, idAccount)
    }
}