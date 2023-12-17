package com.moneo.moneo.data.repository

import com.moneo.moneo.data.remote.model.RekeningsItem
import com.moneo.moneo.data.remote.response.RekeningResponse
import com.moneo.moneo.data.remote.retrofit.ApiService
import retrofit2.Response

class RekeningRepository(private val apiService: ApiService) {
    suspend fun getAllRekekning(token: String, idAccount: String): Response<RekeningResponse> {
        return apiService.getAllRekening(token, idAccount)
    }

    suspend fun insertRekening(token: String, idAccount: String, rekening: RekeningsItem): Response<RekeningResponse> {
        return apiService.insertRekening(token, idAccount, rekening)
    }
}