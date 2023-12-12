package com.moneo.moneo.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.moneo.moneo.data.remote.response.RekapResponse
import com.moneo.moneo.data.remote.retrofit.ApiService
import com.moneo.moneo.data.result.Result

class RekapRepository (private val apiService: ApiService){
    fun getAllLaporan(idAccount: String): LiveData<Result<RekapResponse>> = liveData {
        emit(Result.Loading)
        try {
            val result = apiService.getAllLaporan(idAccount)
            emit(Result.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }


    companion object {
        @Volatile
        private var INSTANCE: RekapRepository? = null
        fun getInstance(
            apiService: ApiService
        ): RekapRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RekapRepository(apiService)
            }.also { INSTANCE = it }
    }
}