package com.moneo.moneo.ui.rekap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneo.moneo.data.local.rekap.Rekap
import com.moneo.moneo.data.remote.response.Perbandingan
import com.moneo.moneo.data.remote.response.RekapResponse
import com.moneo.moneo.data.remote.response.Summary
import com.moneo.moneo.data.repository.RekapRepository
import kotlinx.coroutines.Dispatchers
import com.moneo.moneo.data.result.Result
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RekapViewModel(
    private val rekapRepository: RekapRepository
) : ViewModel() {

    private var _rekap: LiveData<Result<Summary>>? = null

    fun getLaporanResult(): LiveData<Result<Summary>>? {
        return _rekap
    }

    fun getAllLaporan(idAccount: String, token: String) {
        _rekap = rekapRepository.getAllLaporan(idAccount, token)
    }

    private var _prediksi: LiveData<Result<Perbandingan>>? = null

    fun getPrediksiResult(): LiveData<Result<Perbandingan>>? {
        return _prediksi
    }

    fun getPrediksi(idAccount: String, token: String) {
        _prediksi = rekapRepository.getPrediksi(token, idAccount)
    }
}