package com.moneo.moneo.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.moneo.moneo.data.local.rekening.Rekening
import com.moneo.moneo.data.local.rekening.RekeningDao
import com.moneo.moneo.data.remote.response.RekeningResponse
import com.moneo.moneo.data.remote.response.RekeningsItem
import com.moneo.moneo.data.remote.response.toRekeningsItem
import com.moneo.moneo.data.remote.retrofit.ApiService
import com.moneo.moneo.data.result.Result
import com.moneo.moneo.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RekeningRepository private constructor(
    private val rekeningDao: RekeningDao,
    private val apiService: ApiService,
    private val appExecutors: AppExecutors
) {

    fun getAllRekening(idAccount: String, token: String): LiveData<Result<List<Rekening>>> {
        val result = MediatorLiveData<Result<List<Rekening>>>()

        result.value = Result.Loading
        val client = apiService.getAllRekening(idAccount, token)
        client.enqueue(object : Callback<RekeningResponse> {
            override fun onResponse(
                call: Call<RekeningResponse>,
                response: Response<RekeningResponse>
            ) {
                if (response.isSuccessful) {
//                    val totalSaldo = response.body()?.totalSaldo
                    val rekenings = response.body()?.rekenings
                    val rekeningList = ArrayList<Rekening>()
                    appExecutors.diskIO.execute {
                        rekenings?.forEach { rekeningsItem ->
                            val rekening = Rekening(
                                rekeningsItem.idAccount,
                                rekeningsItem.id,
                                rekeningsItem.judul,
                                rekeningsItem.saldo.toInt()
                            )
                            rekeningList.add(rekening)
                        }
                        rekeningDao.insertRekening(rekeningList)
                    }

                }
            }

            override fun onFailure(call: Call<RekeningResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }
        })

        val localData = rekeningDao.getAllRekening()
        result.addSource(localData) { newData: List<Rekening> ->
            result.value = Result.Success(newData)
        }
        return result
    }

    fun createRekening(idAccount: String, token: String, request: RekeningsItem) {
        val result = MediatorLiveData<Result<RekeningsItem>>()

        result.value = Result.Loading
        val client = apiService.addRekening(idAccount, token, request)
        client.enqueue(object : Callback<RekeningResponse> {
            override fun onResponse(call: Call<RekeningResponse>, response: Response<RekeningResponse>) {
                if (response.isSuccessful) {
                    val rekenings = response.body()?.rekenings
                    Log.d("rekenings", rekenings.toString())
                    if (rekenings != null) {
                        appExecutors.diskIO.execute {
                            rekenings.forEach { rekeningsItem ->
                                val rekening = Rekening(
                                    rekeningsItem.idAccount,
                                    rekeningsItem.id,
                                    rekeningsItem.judul,
                                    rekeningsItem.saldo.toInt()
                                )
                                Log.d("repo create", "$rekening")
                                rekeningDao.createRekening(rekening)
                                result.value = Result.Success(rekening.toRekeningsItem())
                            }
                        }
                    } else {
                        result.value = Result.Error("Failed to parse response")
                    }
                } else {
                    result.value = Result.Error("API request failed")
                }
            }

            override fun onFailure(call: Call<RekeningResponse>, t: Throwable) {
                result.value = Result.Error("API request failed")
            }

        })
    }

    fun editRekening(idAccount: String, token: String, id: Int, request: RekeningsItem) {
        val result = MediatorLiveData<Result<RekeningsItem>>()

        result.value = Result.Loading
        val client = apiService.editRekening(idAccount, token, id, request)
        client.enqueue(object : Callback<RekeningResponse> {
            override fun onResponse(
                call: Call<RekeningResponse>,
                response: Response<RekeningResponse>
            ) {
                if (response.isSuccessful) {
                    val rekenings = response.body()?.rekenings
                    Log.d("rekenings", rekenings.toString())
                    if (rekenings != null) {
                        appExecutors.diskIO.execute {
                            rekenings.forEach { rekeningsItem ->
                                val rekening = Rekening(
                                    rekeningsItem.idAccount,
                                    rekeningsItem.id,
                                    rekeningsItem.judul,
                                    rekeningsItem.saldo.toInt()
                                )
                                Log.d("repo create", "$rekening")
                                rekeningDao.updateRekening(rekening)
                            }
                            result.value = (Result.Success(request))
                        }
                    } else {
                        result.value = Result.Error("Failed to parse response")
                    }
                } else {
                    result.value = Result.Error("API request failed")
                }
            }

            override fun onFailure(call: Call<RekeningResponse>, t: Throwable) {
                result.value = Result.Error("API request failed")
            }

        })
    }

    fun deleteRekening(idAccount: String, token: String, id: Int) {
        val result = MediatorLiveData<Result<RekeningResponse?>>()

        result.value = Result.Loading
        val client = apiService.deleteRekening(idAccount, token, id)
        Log.d("repo", "$id")
        client.enqueue(object : Callback<RekeningResponse> {
            override fun onResponse(
                call: Call<RekeningResponse>,
                response: Response<RekeningResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        appExecutors.diskIO.execute {
                            rekeningDao.deleteRekening(id)
                            result.postValue(Result.Success(responseBody))
                        }
                    } else {
                        result.value = Result.Error("Failed to parse response")
                    }
                } else {
                    result.value = Result.Error("API request failed")
                }
            }

            override fun onFailure(call: Call<RekeningResponse>, t: Throwable) {
                result.value = Result.Error("API request failed")
            }
        })
    }

    companion object {
        @Volatile
        private var INSTANCE: RekeningRepository? = null
        fun getInstance(
            rekeningDao : RekeningDao,
            apiService: ApiService,
            appExecutors: AppExecutors
        ): RekeningRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RekeningRepository(rekeningDao, apiService, appExecutors)
            }.also { INSTANCE = it }
    }
}