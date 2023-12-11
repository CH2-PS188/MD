package com.moneo.moneo

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moneo.moneo.data.repository.AccountRepository
import com.moneo.moneo.data.repository.TransactionRepository
import com.moneo.moneo.di.Injection
import com.moneo.moneo.ui.rekening.AccountViewModel
import com.moneo.moneo.ui.rekening.AddUpdateAccountViewModel
import com.moneo.moneo.ui.transaction.AddTransactionViewModel
import com.moneo.moneo.ui.transaction.TransactionViewModel

class ViewModelFactory private constructor(
    private var transactionRepository: TransactionRepository,
    private var accountRepository: AccountRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            return TransactionViewModel(transactionRepository) as T
        }
        if (modelClass.isAssignableFrom(AddTransactionViewModel::class.java)) {
            return AddTransactionViewModel(transactionRepository) as T
        }
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            return AccountViewModel(accountRepository) as T
        }
        if (modelClass.isAssignableFrom(AddUpdateAccountViewModel::class.java)) {
            return AddUpdateAccountViewModel(accountRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideTransactionRepository(context),
                    Injection.provideAccountRepository(context)
                )
            }.also { instance = it }
    }
}