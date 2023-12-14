package com.moneo.moneo.data.local.transaction

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "transaction")
@Parcelize
data class Transaction (
    @ColumnInfo(name = "date")
    var date: String? = null,

    @ColumnInfo(name = "total")
    var total: Int = 0,

    @ColumnInfo(name = "id_account")
    var idAccount: String? = "UEzUHuSwjQcpDcqBdO8FwkRHOew1",

    @ColumnInfo(name = "description")
    var description: String? = null,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "category")
    var category: String? = null,

    @ColumnInfo(name = "type")
    var type: String? = null,

    @ColumnInfo(name = "rekening")
    var rekening: String? = null,
) : Parcelable