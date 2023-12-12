package com.moneo.moneo.ui.rekap

import androidx.lifecycle.ViewModel
import com.moneo.moneo.data.repository.RekapRepository

class RekapViewModel (
    private val rekapRepository: RekapRepository
): ViewModel() {

        fun getAllLaporan(idAccount: String) = rekapRepository.getAllLaporan(idAccount)

}