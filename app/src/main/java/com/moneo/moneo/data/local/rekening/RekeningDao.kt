package com.moneo.moneo.data.local.rekening

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RekeningDao {

    @Query("SELECT * FROM rekening ORDER BY id ASC")
    fun getAllRekening(): LiveData<List<Rekening>>

    @Insert
    fun createRekening(rekening: Rekening)

    @Update
    fun updateRekening(rekening: Rekening)

    @Delete
    fun deleteRekening(rekening: Rekening)

}