package com.moneo.moneo.ui.setting.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneo.moneo.data.remote.response.PrediksiResponse
import com.moneo.moneo.data.repository.SettingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReminderViewModel(private val settingRepository: SettingRepository): ViewModel() {

    fun getNotifikasi(): LiveData<Boolean> {
        return settingRepository.getNotifikasi()
    }

    fun setNotifikasiEnable(enable: Boolean){
        viewModelScope.launch {
            settingRepository.setNotificationEnabled(enable)
        }
    }

}