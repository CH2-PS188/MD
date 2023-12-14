package com.moneo.moneo.di

import android.content.Context
import com.moneo.moneo.data.local.rekening.RekeningDatabase
import com.moneo.moneo.data.local.transaction.TransactionDatabase
import com.moneo.moneo.data.repository.RekeningRepository
import com.moneo.moneo.data.repository.TransactionRepository

object Injection {
    fun provideTransactionRepository(context: Context): TransactionRepository {
        val transactionDatabase = TransactionDatabase.getDatabase(context)
        val transactionDao = transactionDatabase.transactionDao()
        val rekeningDatabase = RekeningDatabase.getDatabase(context)
        val rekeningDao = rekeningDatabase.rekeningDao()
        return TransactionRepository.getInstance(transactionDao, rekeningDao)
    }

    fun provideAccountRepository(context: Context): RekeningRepository {
        val database = RekeningDatabase.getDatabase(context)
        val dao = database.rekeningDao()
        return RekeningRepository.getInstance(dao)
    }
}