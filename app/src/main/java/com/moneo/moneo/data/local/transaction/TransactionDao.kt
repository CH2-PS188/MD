package com.moneo.moneo.data.local.transaction

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TransactionDao {

    @Query("SELECT * FROM `transaction` ORDER BY date DESC")
    fun getAllTransaction(): LiveData<List<Transaction>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransaction(transaction: List<Transaction>)

    @Insert
    fun createTransaction(transaction: Transaction)

    @Update
    fun updateTransaction(transaction: Transaction)

    @Query("DELETE FROM `transaction` WHERE id = :id")
    fun deleteTransaction(id: Int)
}