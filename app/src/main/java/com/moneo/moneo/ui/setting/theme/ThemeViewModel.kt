package com.moneo.moneo.ui.setting.theme

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.moneo.moneo.data.repository.SettingRepository
import kotlinx.coroutines.launch

class ThemeViewModel(private val settingRepository: SettingRepository): ViewModel() {

    fun getThemeSetting(): LiveData<Boolean> {
        return settingRepository.getThemeSetting()
    }

    fun saveThemeSetting(isLightTheme: Boolean){
        viewModelScope.launch {
            settingRepository.saveThemeSetting(isLightTheme)
        }
    }

}