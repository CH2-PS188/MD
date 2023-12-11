package com.moneo.moneo.di

import android.content.Context
import com.moneo.moneo.data.local.account.AccountDatabase
import com.moneo.moneo.data.local.transaction.TransactionDatabase
import com.moneo.moneo.data.repository.AccountRepository
import com.moneo.moneo.data.repository.TransactionRepository

object Injection {
    fun provideTransactionRepository(context: Context): TransactionRepository {
        val database = TransactionDatabase.getDatabase(context)
        val dao = database.transactionDao()
        return TransactionRepository.getInstance(dao)
    }

    fun provideAccountRepository(context: Context): AccountRepository {
        val database = AccountDatabase.getDatabase(context)
        val dao = database.accountDao()
        return AccountRepository.getInstance(dao)
    }
}