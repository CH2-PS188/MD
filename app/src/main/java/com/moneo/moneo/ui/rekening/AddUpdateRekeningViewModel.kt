package com.moneo.moneo.ui.rekening

import androidx.lifecycle.ViewModel
import com.moneo.moneo.data.local.rekening.Rekening
import com.moneo.moneo.data.repository.RekeningRepository

class AddUpdateRekeningViewModel(private val rekeningRepository: RekeningRepository) : ViewModel() {

    fun createRekening(rekening: Rekening) {
        rekeningRepository.createRekening(rekening)
    }

    fun updateRekening(rekening: Rekening) {
        rekeningRepository.updateRekening(rekening)
    }

    fun deleteRekening(rekening: Rekening) {
        rekeningRepository.deleteRekening(rekening)
    }

}