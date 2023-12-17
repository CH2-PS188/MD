package com.moneo.moneo.ui.rekening

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneo.moneo.data.remote.response.RekeningResponse
import com.moneo.moneo.data.remote.response.TransactionResponse
import com.moneo.moneo.data.repository.RekeningRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AccountViewModel(private val rekeningRepository: RekeningRepository) : ViewModel() {

    private val _success = MutableLiveData<RekeningResponse>()
    val success: LiveData<RekeningResponse> get() = _success

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getAllRekening(token: String, idAccount: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _loading.value = true
            }
            try {
                val response = rekeningRepository.getAllRekekning(token, idAccount)
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        _success.value = response.body()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        _error.value = "Error: ${response.code()}"
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