package com.moneo.moneo.ui.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moneo.moneo.data.repository.TransactionRepository
import com.moneo.moneo.di.TransactionInjection
import com.moneo.moneo.ui.transaction.AddTransactionViewModel

class AddTransactionFactory private constructor(private val transactionRepository: TransactionRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AddTransactionViewModel::class.java)) {
            AddTransactionViewModel(transactionRepository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}