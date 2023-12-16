package com.moneo.moneo.ui.rekap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneo.moneo.data.remote.response.RekapResponse
import com.moneo.moneo.data.repository.RekapRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RekapViewModel(
    private val rekapRepository: RekapRepository
) : ViewModel() {

    private val _success = MutableLiveData<RekapResponse>()
    val success: LiveData<RekapResponse> get() = _success

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getAllLaporan(token: String, idAccount: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _loading.value = true
            }
            try {
                val response = rekapRepository.getAllLaporan(token, idAccount)
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