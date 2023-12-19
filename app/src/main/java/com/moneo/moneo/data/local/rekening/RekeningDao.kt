package com.moneo.moneo.data.local.rekening

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.moneo.moneo.data.remote.response.RekeningsItem

@Dao
interface RekeningDao {

    @Query("SELECT * FROM rekening ORDER BY id ASC")
    fun getAllRekening(): LiveData<List<Rekening>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRekening(rekening: List<Rekening>)

    @Insert
    fun createRekening(rekening: Rekening)

    @Update
    fun updateRekening(rekening: Rekening)

    @Query("DELETE FROM rekening WHERE id = :id")
    fun deleteRekening(id: Int)

}