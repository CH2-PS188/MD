package com.moneo.moneo.data.local.account

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface AccountDao {

    @Query("SELECT * FROM account ORDER BY id ASC")
    fun getAllAccount(): LiveData<List<Account>>

    @Insert
    fun createAccount(account: Account)

    @Update
    fun updateAccount(account: Account)

    @Delete
    fun deleteAccount(account: Account)

}