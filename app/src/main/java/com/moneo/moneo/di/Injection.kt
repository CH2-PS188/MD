package com.moneo.moneo.di

import android.content.Context
import com.moneo.moneo.data.local.account.AccountDatabase
import com.moneo.moneo.data.local.transaction.TransactionDatabase
import com.moneo.moneo.data.network.retrofit.ApiConfig
import com.moneo.moneo.data.repository.AccountRepository
import com.moneo.moneo.data.repository.TransactionRepository

object Injection {
    fun provideTransactionRepository(context: Context): TransactionRepository {
        val database = TransactionDatabase.getDatabase(context)
        val dao = database.transactionDao()
        val api = ApiConfig.getApiService()
        return TransactionRepository.getInstance(dao, api)
    }

    fun provideAccountRepository(context: Context): AccountRepository {
        val database = AccountDatabase.getDatabase(context)
        val dao = database.accountDao()
        val api = ApiConfig.getApiService()
        return AccountRepository.getInstance(dao, api)
    }
}