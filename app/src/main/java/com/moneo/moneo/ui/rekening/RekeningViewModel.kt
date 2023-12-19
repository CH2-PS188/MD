package com.moneo.moneo.ui.rekening

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneo.moneo.data.local.rekening.Rekening
import com.moneo.moneo.data.remote.response.RekeningResponse
import com.moneo.moneo.data.repository.RekeningRepository
import com.moneo.moneo.data.result.Result
import kotlinx.coroutines.launch

class RekeningViewModel(private val rekeningRepository: RekeningRepository) : ViewModel() {

    private var _rekeningList: LiveData<Result<List<Rekening>>>? = null

    fun getRekeningResult(): LiveData<Result<List<Rekening>>>? {
        return _rekeningList
    }

    fun getAllRekening(idAccount: String, token: String) {
        viewModelScope.launch {
            _rekeningList = rekeningRepository.getAllRekening(idAccount, token)
        }
    }

}