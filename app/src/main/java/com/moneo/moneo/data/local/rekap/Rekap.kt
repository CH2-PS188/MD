package com.moneo.moneo.data.local.rekap

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "rekap")
@Parcelize
data class Rekap(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Long = 1,

    @ColumnInfo("totalIncome")
    val totalIncome: String? = null,

    @ColumnInfo("total")
    val total: Int? = null,

    @ColumnInfo("endDate")
    val endDate: String? = null,

    @ColumnInfo("totalExpenses")
    val totalExpenses: String? = null,

    @ColumnInfo("difference")
    val difference: String? = null,

    @ColumnInfo("startDate")
    val startDate: String? = null,

    @ColumnInfo("Date")
    val date: String? = null

) : Parcelable