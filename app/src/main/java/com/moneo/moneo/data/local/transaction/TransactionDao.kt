package com.moneo.moneo.data.local.transaction

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TransactionDao {

    @Query("SELECT * FROM `transaction` ORDER BY date DESC")
    fun getAllTransaction(): LiveData<List<Transaction>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTransaction(transaction: Transaction)

}