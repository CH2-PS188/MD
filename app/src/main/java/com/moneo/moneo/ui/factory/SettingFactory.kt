package com.moneo.moneo.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moneo.moneo.datastore.SettingPreferences
import com.moneo.moneo.ui.setting.thema.ThemaViewModel

@Suppress("UNCHECKED_CAST")
class SettingFactory(private val pref: SettingPreferences): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ThemaViewModel::class.java) -> {
                ThemaViewModel(pref) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}