package com.moneo.moneo.data.remote.retrofit

import com.moneo.moneo.data.local.transaction.Transaction
import com.moneo.moneo.data.remote.response.DataItem
import com.moneo.moneo.data.remote.response.RekeningResponse
import com.moneo.moneo.data.remote.response.RekeningsItem
import com.moneo.moneo.data.remote.response.TransactionResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface ApiService {
    @GET("/{id_account}/rekening")
    fun getAllRekening(
        @Path("id_account") idAccount: String,
        @Header("Authorization") token: String,
    ) : Call<RekeningResponse>

    @POST("/{id_account}/rekening")
    fun addRekening(
        @Path("id_account") idAccount: String,
        @Header("Authorization") token: String,
        @Body request: RekeningsItem
    ) : Call<RekeningResponse>

    @PUT("/{id_account}/rekening/{id}")
    fun editRekening(
        @Path("id_account") idAccount: String,
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body request: RekeningsItem
    ) : Call<RekeningResponse>

    @DELETE("/{id_account}/rekening/{id}")
    fun deleteRekening(
        @Path("id_account") idAccount: String,
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ) : Call<RekeningResponse>

    // Transaksi
    @GET("/{id_account}/transaksi/")
    fun getAllTransaksi(
        @Path("id_account") idAccount: String,
        @Header("Authorization") token: String
    ) : Call<TransactionResponse>

    @POST("/{id_account}/transaksi/{type}")
    fun addTransaksi(
        @Path("id_account") idAccount: String,
        @Header("Authorization") token: String,
        @Path("type") type: String,
        @Body request: DataItem
    ) : Call<TransactionResponse>

    @PUT("/{id_account}/transaksi/{id}")
    fun editTransaksi(
        @Path("id_account") idAccount: String,
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body request: DataItem
    ) : Call<TransactionResponse>

    @DELETE("/{id_account}/transaksi/{id}")
    fun deleteTransaksi(
        @Path("id_account") idAccount: String,
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ) : Call<TransactionResponse>
}