package com.moneo.moneo.ui.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.moneo.moneo.data.local.rekening.Rekening
import com.moneo.moneo.data.local.transaction.Transaction
import com.moneo.moneo.data.repository.TransactionRepository

class AddUpdateTransactionViewModel(private val transactionRepository: TransactionRepository) : ViewModel(){

    val itemListRekening: LiveData<List<Rekening>> = transactionRepository.getAllRekeningForSpinner()

    fun insertTransaction(transaction: Transaction) {
        transactionRepository.insertTransaction(transaction)
    }

    fun updateTransaction(transaction: Transaction) {
        transactionRepository.updateTransaction(transaction)
    }

    fun deleteTransaction(transaction: Transaction) {
        transactionRepository.deleteTransaction(transaction)
    }

    fun setUpdateRekening(rekening: Rekening) {
        transactionRepository.setUpdateRekening(rekening)
    }
}