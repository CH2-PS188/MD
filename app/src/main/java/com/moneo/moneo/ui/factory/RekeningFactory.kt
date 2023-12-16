package com.moneo.moneo.ui.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moneo.moneo.data.repository.RekapRepository
import com.moneo.moneo.data.repository.RekeningRepository
import com.moneo.moneo.di.RekapInjection
import com.moneo.moneo.di.RekeningInjection
import com.moneo.moneo.ui.rekap.RekapViewModel
import com.moneo.moneo.ui.rekening.AccountViewModel

class RekeningFactory private constructor(private val rekeningRepository: RekeningRepository): ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AccountViewModel(rekeningRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object{
        @Volatile
        private var instance: RekeningFactory? = null
        fun getInstance(context: Context):RekeningFactory =
            instance ?: synchronized(this){
                instance ?: RekeningFactory(RekeningInjection.provideRepository(context))
            }
    }

}