package com.moneo.moneo.data.repository

import androidx.lifecycle.LiveData
import com.moneo.moneo.data.local.transaction.Transaction
import com.moneo.moneo.data.local.transaction.TransactionDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TransactionRepository private constructor(
    private val transactionDao: TransactionDao
) {
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    fun getAllTransaction(): LiveData<List<Transaction>> = transactionDao.getAllTransaction()

    fun insertTransaction(transaction: Transaction) {
        executorService.execute {
            transactionDao.insertTransaction(transaction)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: TransactionRepository? = null
        fun getInstance(
            transactionDao: TransactionDao
        ): TransactionRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: TransactionRepository(transactionDao)
            }.also { INSTANCE = it }
    }
}