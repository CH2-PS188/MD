package com.moneo.moneo.ui.transaction

import androidx.lifecycle.ViewModel
import com.moneo.moneo.data.local.transaction.Transaction
import com.moneo.moneo.data.repository.TransactionRepository

class AddTransactionViewModel(private val transactionRepository: TransactionRepository) : ViewModel(){

    fun insertTransaction(transaction: Transaction) {
        transactionRepository.insertTransaction(transaction)
    }

    fun updateTransaction(transaction: Transaction) {
        transactionRepository.updateTransaction(transaction)
    }

    fun deleteTransaction(transaction: Transaction) {
        transactionRepository.deleteTransaction(transaction)
    }
}