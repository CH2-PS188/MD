package com.moneo.moneo.di

import android.content.Context
import com.moneo.moneo.data.local.TransactionDatabase
import com.moneo.moneo.data.repository.TransactionRepository

object Injection {
    fun provideRepository(context: Context): TransactionRepository {
        val database = TransactionDatabase.getDatabase(context)
        val dao = database.transactionDao()
        return TransactionRepository.getInstance(dao)
    }
}