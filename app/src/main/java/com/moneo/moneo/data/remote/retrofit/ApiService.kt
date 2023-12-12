package com.moneo.moneo.data.remote.retrofit

import com.moneo.moneo.data.remote.response.RekapResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("{id_account}/laporan")
    suspend fun getAllLaporan(
        @Path("id_account")
        idAccount: String
    ): RekapResponse
}