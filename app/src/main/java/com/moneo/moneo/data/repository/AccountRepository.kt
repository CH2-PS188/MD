package com.moneo.moneo.data.repository

import androidx.lifecycle.LiveData
import com.moneo.moneo.data.local.account.Account
import com.moneo.moneo.data.local.account.AccountDao
import com.moneo.moneo.data.local.transaction.TransactionDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AccountRepository private constructor(
    private val accountDao: AccountDao
) {
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    fun getAllAccounts(): LiveData<List<Account>> = accountDao.getAllAccount()

    fun createAccount(account: Account) {
        executorService.execute {
            accountDao.createAccount(account)
        }
    }

    fun updateAccount(account: Account) {
        executorService.execute {
            accountDao.updateAccount(account)
        }
    }

    fun deleteAccount(account: Account) {
        executorService.execute {
            accountDao.deleteAccount(account)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AccountRepository? = null
        fun getInstance(
            accountDao : AccountDao
        ): AccountRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: AccountRepository(accountDao)
            }.also { INSTANCE = it }
    }
}