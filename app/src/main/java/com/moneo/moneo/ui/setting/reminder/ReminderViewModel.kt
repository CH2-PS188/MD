package com.moneo.moneo.ui.setting.reminder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneo.moneo.data.remote.response.PrediksiResponse
import com.moneo.moneo.data.repository.PrediksiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReminderViewModel(private val prediksiRepository: PrediksiRepository): ViewModel() {

    private val _success = MutableLiveData<PrediksiResponse>()
    val success: LiveData<PrediksiResponse> get() = _success

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error


    private val _isNotificationEnabled = MutableStateFlow(false)
    val isNotificationEnabled: StateFlow<Boolean>
        get() = _isNotificationEnabled


    init {
        viewModelScope.launch {
            _isNotificationEnabled.value = prediksiRepository.isNotificationEnabled()
        }
    }

    fun setNotificationEnabled(enabled: Boolean) {
        viewModelScope.launch (Dispatchers.IO){
            _isNotificationEnabled.value = enabled
            prediksiRepository.setNotificationEnabled(enabled)
        }
    }

    fun getPrediksi(token: String, idAccount: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = prediksiRepository.getPrediksi(token, idAccount)
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        _success.value = response.body()
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