package com.moneo.moneo.ui.rekening

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.moneo.moneo.data.local.rekening.Rekening
import com.moneo.moneo.data.repository.RekeningRepository

class RekeningViewModel(private val rekeningRepository: RekeningRepository) : ViewModel() {

    fun getAllRekening(): LiveData<List<Rekening>> = rekeningRepository.getAllRekening()


    val id = "12345"

}