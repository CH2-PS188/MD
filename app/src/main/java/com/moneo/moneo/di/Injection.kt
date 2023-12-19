package com.moneo.moneo.di

import android.content.Context
import com.moneo.moneo.data.local.rekening.RekeningDatabase
import com.moneo.moneo.data.local.transaction.TransactionDatabase
import com.moneo.moneo.data.remote.retrofit.ApiConfig
import com.moneo.moneo.data.remote.retrofit.ApiService
import com.moneo.moneo.data.repository.RekeningRepository
import com.moneo.moneo.data.repository.TransactionRepository
import com.moneo.moneo.utils.AppExecutors

object Injection {
    fun provideTransactionRepository(context: Context): TransactionRepository {
        val transactionDatabase = TransactionDatabase.getDatabase(context)
        val transactionDao = transactionDatabase.transactionDao()
        val rekeningDatabase = RekeningDatabase.getDatabase(context)
        val rekeningDao = rekeningDatabase.rekeningDao()
        val apiService = ApiConfig.getApiService()
        val appExecutors = AppExecutors()
        return TransactionRepository.getInstance(transactionDao, rekeningDao, apiService, appExecutors)
    }

    fun provideAccountRepository(context: Context): RekeningRepository {
        val database = RekeningDatabase.getDatabase(context)
        val dao = database.rekeningDao()
        val apiService = ApiConfig.getApiService()
        val appExecutors = AppExecutors()
        return RekeningRepository.getInstance(dao, apiService, appExecutors)
    }
}