package com.moneo.moneo.ui.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moneo.moneo.data.repository.RekapRepository
import com.moneo.moneo.di.RekapInjection
import com.moneo.moneo.ui.MainViewModel

class RekapFactory private constructor(private val rekapRepository: RekapRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(rekapRepository) as T
    }

    companion object{
        @Volatile
        private var instance: RekapFactory? = null
        fun getInstance(context: Context):RekapFactory =
            instance ?: synchronized(this){
                instance ?: RekapFactory(RekapInjection.provideRepository(context))
            }
    }
}