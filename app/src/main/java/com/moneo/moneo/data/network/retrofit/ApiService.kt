package com.moneo.moneo.data.network.retrofit

import com.moneo.moneo.data.network.model.AccountResponse
import com.moneo.moneo.data.network.model.TransactionResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    // Transaction

    @GET("/{id_account}/transaksi")
    fun getAllTransactions(
        @Path("id_account")
        idAccount: String
    ): TransactionResponse

    @POST("/{id_account}/transaksi/{type}")
    fun insertTransaction(
        @Path("id_account")
        idAccount: String,
        @Path("type")
        type: String
    ): TransactionResponse

    @PUT("/{id_account}/transaksi/{id}")
    fun updateTransactionById(
        @Path("id_account")
        idAccount: String,
        @Path("id")
        id: Int
    ): TransactionResponse

    @DELETE("/{id_account}/transaksi/{id}")
    fun deleteTransactionById(
        @Path("id_account")
        idAccount: String,
        @Path("id")
        id: Int
    ): TransactionResponse

    // Account

    @GET("/{id_account}/transaksi")
    fun getAllAccounts(
        @Path("id_account")
        idAccount: String
    ): AccountResponse

}