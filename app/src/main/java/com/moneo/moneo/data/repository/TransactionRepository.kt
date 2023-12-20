package com.moneo.moneo.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.moneo.moneo.data.local.rekening.Rekening
import com.moneo.moneo.data.local.rekening.RekeningDao
import com.moneo.moneo.data.local.transaction.Transaction
import com.moneo.moneo.data.local.transaction.TransactionDao
import com.moneo.moneo.data.remote.response.DataItem
import com.moneo.moneo.data.remote.response.RekeningsItem
import com.moneo.moneo.data.remote.response.TransactionResponse
import com.moneo.moneo.data.remote.response.toDataItem
import com.moneo.moneo.data.remote.retrofit.ApiService
import com.moneo.moneo.data.result.Result
import com.moneo.moneo.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionRepository private constructor(
    private val transactionDao: TransactionDao,
    private val rekeningDao: RekeningDao,
    private val apiService: ApiService,
    private val appExecutors: AppExecutors
) {

    fun getAllTransaction(idAccount: String, token: String): LiveData<Result<List<Transaction>>> {
        val result = MediatorLiveData<Result<List<Transaction>>>()

        Log.d("idaccount", idAccount + token)
        result.value = Result.Loading
        val client = apiService.getAllTransaksi(idAccount, token)
        client.enqueue(object : Callback<TransactionResponse> {
            override fun onResponse(
                call: Call<TransactionResponse>,
                response: Response<TransactionResponse>
            ) {
                Log.d("response all", "$response")
                if (response.isSuccessful) {
                    Log.d("response is succesful", "${response.body()}")
                    val transactions = response.body()?.data
                    val transactionList = ArrayList<Transaction>()
                    if (transactions != null) {
                        Log.d("response not null", "$transactions")
                        appExecutors.diskIO.execute {
                            transactions.forEach { dataItem ->
                                val transaction = Transaction(
                                    dataItem.date,
                                    dataItem.total,
                                    dataItem.idAccount,
                                    dataItem.description,
                                    dataItem.id,
                                    dataItem.title,
                                    dataItem.category,
                                    dataItem.type,
                                    dataItem.account
                                )
                                Log.d("transaction class", "$transaction")
                                transactionList.add(transaction)
                            }
                            transactionDao.insertTransaction(transactionList)
                        }
                    } else {
                        Log.d("response is null", "${response.body()}")
                        result.value = Result.Error("Failed to parse response")
                    }
                } else {
                    Log.d("response is failed", "${response.body()}")
                    result.value = Result.Error("API request failed")
                }
            }

            override fun onFailure(call: Call<TransactionResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }

        })
        val localData = transactionDao.getAllTransaction()
        result.addSource(localData) { newData: List<Transaction> ->
            result.value = Result.Success(newData)
        }
        return result
    }

    fun insertTransaction(idAccount: String, token: String, request: DataItem) {
        val result = MediatorLiveData<Result<DataItem>>()

        result.value = Result.Loading
        val client = apiService.addTransaksi(idAccount, token, request.type, request)
        client.enqueue(object : Callback<TransactionResponse> {
            override fun onResponse(
                call: Call<TransactionResponse>,
                response: Response<TransactionResponse>
            ) {
                Log.d("response", response.toString())
                if (response.isSuccessful) {
                    Log.d("response is succesful", "${response.body()}")
                    val transactions = response.body()?.data
                    if (transactions != null) {
                        Log.d("response not null", "$transactions")
                        appExecutors.diskIO.execute {
                            transactions.forEach { dataItem ->
                                val transaction = Transaction(
                                    dataItem.date,
                                    dataItem.total,
                                    dataItem.idAccount,
                                    dataItem.description,
                                    dataItem.id,
                                    dataItem.title,
                                    dataItem.category,
                                    dataItem.type,
                                    dataItem.account
                                )
                                Log.d("transaction class", "$transaction")
                                transactionDao.createTransaction(transaction)
                                result.value = Result.Success(transaction.toDataItem())
                            }
                        }
                    } else {
                        result.value = Result.Error("Failed to parse response")
                    }
                } else {
                    result.value = Result.Error("API request failed")
                }
            }

            override fun onFailure(call: Call<TransactionResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }

        })
    }

    fun updateTransaction(idAccount: String, token: String, id: Int, request: DataItem) {
        val result = MediatorLiveData<Result<DataItem>>()

        result.value = Result.Loading
        Log.d("update repo", "$request")
        val client = apiService.editTransaksi(idAccount, token, id, request)
        client.enqueue(object : Callback<TransactionResponse> {
            override fun onResponse(
                call: Call<TransactionResponse>,
                response: Response<TransactionResponse>
            ) {
                Log.d("edit", "$response")
                if (response.isSuccessful) {
                    Log.d("edit is succesful", "${response.body()}")
                    val transactions = response.body()?.data
                    if (transactions != null) {
                        Log.d("edit not null", "$transactions")
                        appExecutors.diskIO.execute {
                            transactions.forEach { dataItem ->
                                val transaction = Transaction(
                                    dataItem.date,
                                    dataItem.total,
                                    dataItem.idAccount,
                                    dataItem.description,
                                    dataItem.id,
                                    dataItem.title,
                                    dataItem.category,
                                    dataItem.type,
                                    dataItem.account
                                )
                                Log.d("edit transaction class", "$transaction")
                                transactionDao.updateTransaction(transaction)
                                result.value = Result.Success(transaction.toDataItem())
                            }
                        }
                    } else {
                        result.value = Result.Error("Failed to parse response")
                    }
                } else {
                    result.value = Result.Error("API request failed")
                }
            }

            override fun onFailure(call: Call<TransactionResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }

        })
    }

    fun deleteTransaction(idAccount: String, token: String, id: Int) {
        val result = MediatorLiveData<Result<TransactionResponse>>()

        result.value = Result.Loading
        val client = apiService.deleteTransaksi(idAccount, token, id)
        Log.d("repo delete", "${id}")
        client.enqueue(object : Callback<TransactionResponse> {
            override fun onResponse(
                call: Call<TransactionResponse>,
                response: Response<TransactionResponse>
            ) {
                Log.d("response delete", "$response")
                if (response.isSuccessful) {
                    Log.d("delete is succesful", "${response.body()}")
                    val transactions = response.body()
                    if (transactions != null) {
                        Log.d("delete not null", "$transactions")
                        appExecutors.diskIO.execute {
                            transactionDao.deleteTransaction(id)
                            result.postValue(Result.Success(transactions))
                        }
                    } else {
                        result.value = Result.Error("Failed to parse response delete")
                    }
                } else {
                    result.value = Result.Error("API request failed delete")
                }
            }

            override fun onFailure(call: Call<TransactionResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }

        })
        appExecutors.diskIO.execute {
            transactionDao.deleteTransaction(id)
        }
    }

    fun getRekeningForTransaction(): LiveData<List<Rekening>> {
        return rekeningDao.getAllRekening()
    }

    companion object {
        @Volatile
        private var INSTANCE: TransactionRepository? = null
        fun getInstance(
            transactionDao: TransactionDao,
            rekeningDao: RekeningDao,
            apiService: ApiService,
            appExecutors: AppExecutors
        ): TransactionRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: TransactionRepository(transactionDao, rekeningDao, apiService, appExecutors)
            }.also { INSTANCE = it }
    }
}