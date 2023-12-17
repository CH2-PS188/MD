package com.moneo.moneo.ui.transaction

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneo.moneo.data.remote.model.TransactionItem
import com.moneo.moneo.data.remote.response.TransactionResponse
import com.moneo.moneo.data.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddTransactionViewModel(private val transactionRepository: TransactionRepository) : ViewModel(){

    private val _success = MutableLiveData<TransactionResponse>()
    val success: LiveData<TransactionResponse> get() = _success

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun insertTransactionPemasukan(token: String, idAccount: String, request: TransactionItem) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _loading.value = true
            }
            try {
                val response = transactionRepository.insertTransactionPemasukan(token, idAccount, request)
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        _success.value = response.body()
                        Log.e("Add Transaction", "Success ${response.code()}")
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        val errorBody = response.errorBody()?.string()
                        _error.value = "Error ${response.code()}: $errorBody"
                        Log.e("Add Transaction", "Error: ${response.code()}: $errorBody")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = "Error: ${e.message}"
                    Log.e("Add Transaction", "Error: ${e.message}")
                }
            }
        }
    }

    fun insertTransactionPengeluaran(token: String, idAccount: String, request: TransactionItem) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _loading.value = true
            }
            try {
                val response = transactionRepository.insertTransactionPengeluaran(token, idAccount, request)
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        _success.value = response.body()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        val errorBody = response.errorBody()?.string()
                        _error.value = "Error ${response.code()}: $errorBody"
                        Log.e("Add Transaction", "Error: ${response.code()}: $errorBody")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = "Error: ${e.message}"
                }
            }
        }
    }
}