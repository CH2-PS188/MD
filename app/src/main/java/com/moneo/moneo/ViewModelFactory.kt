package com.moneo.moneo

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moneo.moneo.data.repository.RekapRepository
import com.moneo.moneo.data.repository.RekeningRepository
import com.moneo.moneo.data.repository.SettingRepository
import com.moneo.moneo.data.repository.TransactionRepository
import com.moneo.moneo.di.Injection
import com.moneo.moneo.ui.rekap.RekapViewModel
import com.moneo.moneo.ui.rekening.RekeningViewModel
import com.moneo.moneo.ui.rekening.AddUpdateRekeningViewModel
import com.moneo.moneo.ui.setting.reminder.ReminderViewModel
import com.moneo.moneo.ui.setting.theme.ThemeViewModel
import com.moneo.moneo.ui.transaction.AddUpdateTransactionViewModel
import com.moneo.moneo.ui.transaction.TransactionViewModel

class ViewModelFactory private constructor(
    private var transactionRepository: TransactionRepository,
    private var rekeningRepository: RekeningRepository,
    private var rekapRepository: RekapRepository,
    private val settingRepository: SettingRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            return TransactionViewModel(transactionRepository) as T
        }
        if (modelClass.isAssignableFrom(AddUpdateTransactionViewModel::class.java)) {
            return AddUpdateTransactionViewModel(transactionRepository) as T
        }
        if (modelClass.isAssignableFrom(RekeningViewModel::class.java)) {
            return RekeningViewModel(rekeningRepository) as T
        }
        if (modelClass.isAssignableFrom(AddUpdateRekeningViewModel::class.java)) {
            return AddUpdateRekeningViewModel(rekeningRepository) as T
        }
        if (modelClass.isAssignableFrom(RekapViewModel::class.java)) {
            return RekapViewModel(rekapRepository) as T
        }
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel(settingRepository) as T
        }
        if (modelClass.isAssignableFrom(ReminderViewModel::class.java)) {
            return ReminderViewModel(settingRepository) as T
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
                    Injection.provideAccountRepository(context),
                    Injection.provideRekapRepository(context),
                    Injection.provideSettingRepository(context)
                )
            }.also { instance = it }
    }
}