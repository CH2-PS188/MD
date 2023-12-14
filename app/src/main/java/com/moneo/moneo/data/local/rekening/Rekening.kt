package com.moneo.moneo.data.local.rekening

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "rekening")
@Parcelize
data class Rekening (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var rekeningId : Int = 0,

    @ColumnInfo(name = "name")
    var name : String? = null,

    @ColumnInfo(name = "balance")
    var balance : Int = 0,
) : Parcelable