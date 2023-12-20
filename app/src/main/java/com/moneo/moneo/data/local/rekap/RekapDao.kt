package com.moneo.moneo.data.local.rekap

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moneo.moneo.data.local.rekening.Rekening

@Dao
interface RekapDao {

    @Query("SELECT * FROM rekap ORDER BY endDate DESC")
    fun getAllRekap(): LiveData<Rekap>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRekap(rekap: Rekap)

}