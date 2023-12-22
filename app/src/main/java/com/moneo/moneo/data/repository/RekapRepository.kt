package com.moneo.moneo.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.moneo.moneo.data.remote.response.Perbandingan
import com.moneo.moneo.data.remote.response.PrediksiResponse
import com.moneo.moneo.data.remote.response.RekapResponse
import com.moneo.moneo.data.remote.response.Summary
import com.moneo.moneo.data.remote.retrofit.ApiModelService
import com.moneo.moneo.data.result.Result
import com.moneo.moneo.data.remote.retrofit.ApiService
import com.moneo.moneo.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RekapRepository private constructor(
//    private val rekapDao: RekapDao,
    private val apiService: ApiService,
    private val apiModelService: ApiModelService,
    private val appExecutors: AppExecutors
){

    fun getAllLaporan(idAccount: String, token: String): LiveData<Result<Summary>> {
        val result = MediatorLiveData<Result<Summary>>()

        result.value = Result.Loading
        val client = apiService.getAllLaporan(idAccount, token)
        client.enqueue(object : Callback<RekapResponse> {
            override fun onResponse(call: Call<RekapResponse>, response: Response<RekapResponse>) {
                Log.d("laporan response", "$response")
                if (response.isSuccessful) {
                    val laporans = response.body()?.summary
                    Log.d("laporan summary", "$laporans")
                    appExecutors.diskIO.execute {
                        laporans.let { summary ->
                            val rekap = Summary(
                                summary?.totalIncome,
                                summary?.total,
                                summary?.endDate,
                                summary?.totalExpenses,
                                summary?.difference,
                                summary?.startDate,
                                summary?.date,
                                summary?.averageIncome,
                                summary?.averageExpenses
                            )
                            result.postValue(Result.Success(rekap))
                        }
                    }
                } else {
                    Log.d("gagal", response.message())
                    result.value = Result.Error(response.message())
                }
            }

            override fun onFailure(call: Call<RekapResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }

        })
        return result
    }

    fun getPrediksi(token: String, idAccount: String): LiveData<Result<Perbandingan>> {
        val result = MediatorLiveData<Result<Perbandingan>>()

        result.value = Result.Loading
        val client = apiModelService.getPrediksi(token, idAccount)
        Log.d("prediksi on going", "$client")
        client.enqueue(object : Callback<PrediksiResponse> {
            override fun onResponse(
                call: Call<PrediksiResponse>,
                response: Response<PrediksiResponse>
            ) {
                Log.d("prediksi call", "$call")
                Log.d("prediksi response", "$response")
                if (response.isSuccessful) {
                    val prediksi = response.body()?.perbandingan
                    if (prediksi != null) {
                        appExecutors.diskIO.execute {
                            Log.d("prediksi perbandingan", "$prediksi")
                            prediksi.let { ml ->
                                val hasilPrediksi = Perbandingan(
                                    ml.totalPemasukanINR,
                                    ml.tanggalPrediksi,
                                    ml.totalPemasukanIDR,
                                    ml.accuracy,
                                    ml.risk,
                                    ml.totalWaktuLoading
                                )
                                result.postValue(Result.Success(hasilPrediksi))
                            }
                        }
                    } else {
                        Log.d("kosong", response.message())
                        result.value = Result.Error(response.message())
                    }
                } else {
                    Log.d("gagal", response.message())
                    result.value = Result.Error(response.message())
                }
            }

            override fun onFailure(call: Call<PrediksiResponse>, t: Throwable) {
                Log.d("onFailure: ", t.message.toString())
                result.value = Result.Error(t.message.toString())
            }

        })
        return result
    }



    companion object {
        @Volatile
        private var INSTANCE: RekapRepository? = null
        fun getInstance(
//            rekapDao: RekapDao,
            apiService: ApiService,
            apiModelService: ApiModelService,
            appExecutors: AppExecutors
        ): RekapRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RekapRepository(apiService, apiModelService, appExecutors)
            }.also { INSTANCE = it }
    }
}
