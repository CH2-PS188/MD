package com.moneo.moneo.data.local.account

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AccountDao {

    @Query("SELECT * FROM account ORDER BY id ASC")
    fun getAllAccount(): LiveData<List<Account>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createAccount(account: Account)

}