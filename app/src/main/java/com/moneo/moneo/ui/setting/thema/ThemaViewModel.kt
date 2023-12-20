package com.moneo.moneo.ui.setting.thema

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.moneo.moneo.datastore.SettingPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ThemaViewModel(private val pref: SettingPreferences): ViewModel() {

    fun getThemeSetting(): LiveData<Boolean>{
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isLightTheme: Boolean){
        viewModelScope.launch {
            pref.saveThemeSetting(isLightTheme)
        }
    }
}