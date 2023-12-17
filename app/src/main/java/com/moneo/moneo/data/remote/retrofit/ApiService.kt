package com.moneo.moneo.data.remote.retrofit

import com.moneo.moneo.data.remote.model.RekeningsItem
import com.moneo.moneo.data.remote.model.TransactionItem
import com.moneo.moneo.data.remote.response.RekeningResponse
import com.moneo.moneo.data.remote.response.RekapResponse
import com.moneo.moneo.data.remote.response.TransactionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    //Transaksi
    @GET("/{id_account}/transaksi/")
    suspend fun getAllTransactions(
        @Header("Authorization") token: String,
        @Path("id_account") idAccount: String
    ): Response<TransactionResponse>

    @Headers("Accept: application/json", "Content-type: application/json")
    @POST("/{id_account}/transaksi/pemasukan")
     suspend fun insertTransactionPemasukan(
        @Path("id_account") idAccount: String,
        @Header("Authorization") token: String,
        @Body request: TransactionItem
    ): Response<TransactionResponse>

    @Headers("Accept: application/json", "Content-type: application/json")
    @POST("/{id_account}/transaksi/pengeluaran")
     suspend fun insertTransactionPengeluaran(
        @Path("id_account") idAccount: String,
        @Header("Authorization") token: String,
        @Body request: TransactionItem
    ): Response<TransactionResponse>


    // Rekening

    @GET("/{id_account}/rekening")
    suspend fun getAllRekening(
        @Path("id_account") idAccount: String,
        @Header("Authorization") token: String,
    ): Response<RekeningResponse>

    @POST("/{id_account}/rekening")
    suspend fun insertRekening(
        @Path("id_account") idAccount: String,
        @Header("Authorization") token: String,
        @Body request: RekeningsItem
    ): Response<RekeningResponse>


    //Laporan
    @GET("{id_account}/laporan")
    suspend fun getAllLaporan(
        @Header("Authorization") token: String,
        @Path("id_account") idAccount: String
    ): Response<RekapResponse>
}