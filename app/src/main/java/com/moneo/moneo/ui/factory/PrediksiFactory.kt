package com.moneo.moneo.ui.factory

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moneo.moneo.data.repository.PrediksiRepository
import com.moneo.moneo.di.PrediksiInjection
import com.moneo.moneo.ui.setting.reminder.ReminderViewModel

class PrediksiFactory(private val prediksiRepository: PrediksiRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReminderViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReminderViewModel(prediksiRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object{
        @Volatile
        private var instance: PrediksiFactory? = null
        @RequiresApi(Build.VERSION_CODES.N)
        fun getInstance(context: Context):PrediksiFactory =
            instance ?: synchronized(this){
                instance ?: PrediksiFactory(PrediksiInjection.provideRepository(context))
            }
    }

}