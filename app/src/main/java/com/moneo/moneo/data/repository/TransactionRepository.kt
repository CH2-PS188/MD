package com.moneo.moneo.data.repository

import androidx.lifecycle.LiveData
import com.moneo.moneo.data.local.rekening.Rekening
import com.moneo.moneo.data.local.rekening.RekeningDao
import com.moneo.moneo.data.local.transaction.Transaction
import com.moneo.moneo.data.local.transaction.TransactionDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TransactionRepository private constructor(
    private val transactionDao: TransactionDao,
    private val rekeningDao: RekeningDao
) {
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    fun getAllTransaction(): LiveData<List<Transaction>> {
        return transactionDao.getAllTransaction()
    }

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

    fun getRekeningForTransaction(): LiveData<List<Rekening>> {
        return rekeningDao.getAllRekening()
    }

    companion object {
        @Volatile
        private var INSTANCE: TransactionRepository? = null
        fun getInstance(
            transactionDao: TransactionDao,
            rekeningDao: RekeningDao
        ): TransactionRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: TransactionRepository(transactionDao, rekeningDao)
            }.also { INSTANCE = it }
    }
}