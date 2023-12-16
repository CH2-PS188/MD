package com.moneo.moneo.ui.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneo.moneo.data.local.rekening.Rekening
import com.moneo.moneo.data.local.transaction.Transaction
import com.moneo.moneo.data.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddUpdateTransactionViewModel(private val transactionRepository: TransactionRepository) : ViewModel(){

    private val _rekeningList = MediatorLiveData<List<Rekening>>()
    val rekeningList: LiveData<List<Rekening>> =_rekeningList

    init {
        _rekeningList.addSource(transactionRepository.getRekeningForTransaction()) { listRekening ->
            _rekeningList.value = listRekening
        }
    }

    fun insertTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionRepository.insertTransaction(transaction)
        }
    }

    fun updateTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionRepository.updateTransaction(transaction)
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionRepository.deleteTransaction(transaction)
        }
    }
}