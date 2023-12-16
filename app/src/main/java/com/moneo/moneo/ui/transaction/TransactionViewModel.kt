package com.moneo.moneo.ui.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.moneo.moneo.data.local.transaction.Transaction
import com.moneo.moneo.data.repository.TransactionRepository

class TransactionViewModel (private val transactionRepository: TransactionRepository) : ViewModel() {

    fun getAllTransaction(): LiveData<List<Transaction>> {
        return transactionRepository.getAllTransaction()
    }

}