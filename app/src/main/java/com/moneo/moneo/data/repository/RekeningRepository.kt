package com.moneo.moneo.data.repository

import androidx.lifecycle.LiveData
import com.moneo.moneo.data.local.rekening.Rekening
import com.moneo.moneo.data.local.rekening.RekeningDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class RekeningRepository private constructor(
    private val rekeningDao: RekeningDao
) {
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    fun getAllRekening(): LiveData<List<Rekening>> = rekeningDao.getAllRekening()

    fun createRekening(rekening: Rekening) {
        executorService.execute {
            rekeningDao.createRekening(rekening)
        }
    }

    fun updateRekening(rekening: Rekening) {
        executorService.execute {
            rekeningDao.updateRekening(rekening)
        }
    }

    fun deleteRekening(rekening: Rekening) {
        executorService.execute {
            rekeningDao.deleteRekening(rekening)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: RekeningRepository? = null
        fun getInstance(
            rekeningDao : RekeningDao
        ): RekeningRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RekeningRepository(rekeningDao)
            }.also { INSTANCE = it }
    }
}