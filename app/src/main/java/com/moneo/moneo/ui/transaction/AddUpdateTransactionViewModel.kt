package com.moneo.moneo.ui.transaction

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneo.moneo.data.local.rekening.Rekening
import com.moneo.moneo.data.local.transaction.Transaction
import com.moneo.moneo.data.remote.response.DataItem
import com.moneo.moneo.data.remote.response.RekeningsItem
import com.moneo.moneo.data.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddUpdateTransactionViewModel(private val transactionRepository: TransactionRepository) : ViewModel(){

    private val _rekeningList = MediatorLiveData<List<Rekening>>()
    val rekeningList: LiveData<List<Rekening>> =_rekeningList

    init {
        _rekeningList.addSource(transactionRepository.getRekeningForTransaction()) { listRekening ->
            _rekeningList.value = listRekening
        }
    }

    fun insertTransaction(idAccount: String, token: String, transaction: DataItem) {
        transactionRepository.insertTransaction(idAccount, token, transaction)
    }

    fun updateTransaction(idAccount: String, token: String, id: Int, transaction: DataItem) {
        Log.d("update viewmodel", "$transaction")
        transactionRepository.updateTransaction(idAccount, token, id, transaction)
    }

    fun deleteTransaction(idAccount: String, token: String, id: Int) {
        transactionRepository.deleteTransaction(idAccount, token, id)
    }
}