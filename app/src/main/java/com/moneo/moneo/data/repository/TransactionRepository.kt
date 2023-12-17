package com.moneo.moneo.data.repository

import com.moneo.moneo.data.remote.model.TransactionItem
import com.moneo.moneo.data.remote.response.TransactionResponse
import com.moneo.moneo.data.remote.retrofit.ApiService
import retrofit2.Response

class TransactionRepository (private val apiService: ApiService) {

    suspend fun getTransactions(token: String, idAccount: String): Response<TransactionResponse> {
        return apiService.getAllTransactions(token, idAccount)
    }

    suspend fun insertTransactionPemasukan(token: String, idAccount: String, request: TransactionItem): Response<TransactionResponse> {
        return apiService.insertTransactionPemasukan(token, idAccount, request)
    }
    suspend fun insertTransactionPengeluaran(token: String, idAccount: String, request: TransactionItem): Response<TransactionResponse> {
        return apiService.insertTransactionPengeluaran(token, idAccount, request)
    }
}