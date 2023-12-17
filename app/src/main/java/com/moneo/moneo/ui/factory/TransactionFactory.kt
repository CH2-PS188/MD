package com.moneo.moneo.ui.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moneo.moneo.data.repository.TransactionRepository
import com.moneo.moneo.di.TransactionInjection
import com.moneo.moneo.ui.transaction.AddTransactionViewModel
import com.moneo.moneo.ui.transaction.TransactionViewModel

class TransactionFactory private constructor(private val transactionRepository: TransactionRepository): ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(TransactionViewModel::class.java) -> {
                TransactionViewModel(transactionRepository) as T
            }
            modelClass.isAssignableFrom(AddTransactionViewModel::class.java) -> {
                AddTransactionViewModel(transactionRepository) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }


    companion object {
        @Volatile
        private var instance: TransactionFactory? = null
        fun getInstance(context: Context): TransactionFactory =
            instance ?: synchronized(this) {
                instance ?: TransactionFactory(TransactionInjection.provideRepository(context))
            }.also { instance = it }
    }

}