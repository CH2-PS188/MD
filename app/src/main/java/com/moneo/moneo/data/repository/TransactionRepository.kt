package com.moneo.moneo.data.repository

import androidx.lifecycle.LiveData
import com.moneo.moneo.data.local.transaction.Transaction
import com.moneo.moneo.data.local.transaction.TransactionDao
import com.moneo.moneo.data.network.retrofit.ApiService
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TransactionRepository private constructor(
    private val transactionDao: TransactionDao,
    private val apiService: ApiService
) {
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    fun getAllTransaction(): LiveData<List<Transaction>> = transactionDao.getAllTransaction()

    fun insertTransaction(transaction: Transaction) {
        executorService.execute {
            transactionDao.insertTransaction(transaction)
        }
    }

    fun updateTransaction(transaction: Transaction) {
        executorService.execute {
            transactionDao.updateTransaction(transaction)
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        executorService.execute {
            transactionDao.deleteTransaction(transaction)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: TransactionRepository? = null
        fun getInstance(
            transactionDao: TransactionDao,
            apiService: ApiService
        ): TransactionRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: TransactionRepository(transactionDao, apiService)
            }.also { INSTANCE = it }
    }
}