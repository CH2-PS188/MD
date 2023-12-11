package com.moneo.moneo.data.local.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction")
data class Transaction (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "date")
    var date: String? = null,

    @ColumnInfo(name = "account")
    var account: String? = null,

    @ColumnInfo(name = "total")
    var total: Int = 0,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "category")
    var category: String? = null,

    @ColumnInfo(name = "description")
    var description: String? = null,


)