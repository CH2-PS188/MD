package com.moneo.moneo.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.moneo.moneo.data.remote.model.TransactionItem
import com.moneo.moneo.data.remote.response.TransactionResponse
import com.moneo.moneo.data.remote.retrofit.ApiService
import com.moneo.moneo.data.result.Result
import retrofit2.Response

class TransactionRepository (private val apiService: ApiService) {

    suspend fun getTransactions(token: String, idAccount: String): Response<TransactionResponse> {
        return apiService.getAllTransactions(token, idAccount)
    }
}