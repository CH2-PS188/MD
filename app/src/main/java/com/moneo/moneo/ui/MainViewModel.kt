package com.moneo.moneo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moneo.moneo.data.remote.response.ListRekapItem
import com.moneo.moneo.data.repository.RekapRepository

class MainViewModel(
    private val rekapRepository: RekapRepository
): ViewModel() {

    fun getAllLaporan(idAccount: String) = rekapRepository.getAllLaporan(idAccount)

}