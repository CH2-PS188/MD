package com.moneo.moneo.ui.rekening

import android.util.Log
import androidx.lifecycle.ViewModel
import com.moneo.moneo.data.local.rekening.Rekening
import com.moneo.moneo.data.remote.response.RekeningsItem
import com.moneo.moneo.data.repository.RekeningRepository

class AddUpdateRekeningViewModel(private val rekeningRepository: RekeningRepository) : ViewModel() {

    fun createRekening(idAccount: String, token: String, rekening: RekeningsItem) {
        rekeningRepository.createRekening(idAccount, token, rekening)
    }

    fun updateRekening(idAccount: String, token: String, id: Int, rekening: RekeningsItem) {
        rekeningRepository.editRekening(idAccount, token, id, rekening)
    }

    fun deleteRekening(idAccount: String, token: String, id: Int) {
        Log.d("view model", "$id")
        rekeningRepository.deleteRekening(idAccount, token, id)
    }

}