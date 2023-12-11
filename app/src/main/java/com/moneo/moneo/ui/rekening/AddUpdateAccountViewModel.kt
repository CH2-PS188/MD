package com.moneo.moneo.ui.rekening

import androidx.lifecycle.ViewModel
import com.moneo.moneo.data.local.account.Account
import com.moneo.moneo.data.repository.AccountRepository

class AddUpdateAccountViewModel(private val accountRepository: AccountRepository) : ViewModel() {

    fun createAccount(account: Account) {
        accountRepository.createAccount(account)
    }

}