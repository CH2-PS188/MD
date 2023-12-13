package com.moneo.moneo.ui.rekening

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.moneo.moneo.data.local.account.Account
import com.moneo.moneo.data.network.model.AccountResponse
import com.moneo.moneo.data.network.model.RekeningsItem
import com.moneo.moneo.data.repository.AccountRepository

class AccountViewModel(private val accountRepository: AccountRepository) : ViewModel() {

    fun getAllAccounts(): LiveData<List<Account>> = accountRepository.getAllAccounts()


    val id = "12345"

    fun getAllRekening(): AccountResponse = accountRepository.getAllAccounts(id)

}