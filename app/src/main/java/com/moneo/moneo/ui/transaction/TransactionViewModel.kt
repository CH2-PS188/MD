package com.moneo.moneo.ui.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneo.moneo.data.local.rekening.Rekening
import com.moneo.moneo.data.local.transaction.Transaction
import com.moneo.moneo.data.repository.TransactionRepository
import com.moneo.moneo.data.result.Result
import kotlinx.coroutines.launch

class TransactionViewModel (private val transactionRepository: TransactionRepository) : ViewModel() {

    private var _transactionList: LiveData<Result<List<Transaction>>>? = null

    fun getTransactionResult(): LiveData<Result<List<Transaction>>>? {
        return _transactionList
    }

    fun getAllTransaction(idAccount: String, token: String) {
        viewModelScope.launch {
            _transactionList = transactionRepository.getAllTransaction(idAccount, token)
        }
    }

}