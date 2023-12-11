package com.moneo.moneo.data.local.account

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "account")
data class Account(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var accountId : Int = 0,

    @ColumnInfo(name = "name")
    var name : String? = null,

    @ColumnInfo(name = "balance")
    var balance : Int = 0,
)