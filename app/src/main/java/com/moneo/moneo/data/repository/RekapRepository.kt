package com.moneo.moneo.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.moneo.moneo.data.local.rekap.Rekap
import com.moneo.moneo.data.local.rekap.RekapDao
import com.moneo.moneo.data.remote.response.RekapResponse
import com.moneo.moneo.data.remote.response.Summary
import com.moneo.moneo.data.remote.response.toRekeningsItem
import com.moneo.moneo.data.result.Result
import com.moneo.moneo.data.remote.retrofit.ApiService
import com.moneo.moneo.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RekapRepository private constructor(
//    private val rekapDao: RekapDao,
    private val apiService: ApiService,
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
                }
            }

            override fun onFailure(call: Call<RekapResponse>, t: Throwable) {
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
            appExecutors: AppExecutors
        ): RekapRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RekapRepository(apiService, appExecutors)
            }.also { INSTANCE = it }
    }
}
