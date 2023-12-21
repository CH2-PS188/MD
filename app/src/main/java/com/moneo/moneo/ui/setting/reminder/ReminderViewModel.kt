package com.moneo.moneo.ui.setting.reminder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.moneo.moneo.data.remote.response.PrediksiResponse
import com.moneo.moneo.datastore.SettingPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReminderViewModel(private val pref: SettingPreferences): ViewModel() {

    fun getNotifikasi(): LiveData<Boolean>{
        return pref.getNotifikasi().asLiveData()
    }

    fun setNotifikasiEnable(enable: Boolean){
        viewModelScope.launch {
            pref.setNotificationEnabled(enable)
        }
    }

}