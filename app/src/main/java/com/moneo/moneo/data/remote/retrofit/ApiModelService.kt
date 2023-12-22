package com.moneo.moneo.data.remote.retrofit

import com.moneo.moneo.data.remote.response.PrediksiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiModelService {
    @GET("{id_account}/prediksi")
    fun getPrediksi(
        @Header("Authorization") token: String,
        @Path("id_account") idAccount: String
    ): Call<PrediksiResponse>
}