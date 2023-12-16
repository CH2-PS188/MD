package com.moneo.moneo.data.remote.retrofit

import com.moneo.moneo.data.remote.response.RekapResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {

    @GET("{id_account}/laporan")
    suspend fun getAllLaporan(
        @Header("Authorization") token: String,
        @Path("id_account") idAccount: String
    ): Response<RekapResponse>
}