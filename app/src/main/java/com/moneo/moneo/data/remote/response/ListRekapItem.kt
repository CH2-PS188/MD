package com.moneo.moneo.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListRekapItem(
    @field:SerializedName("totalIncome")
    val totalIncome: String,

    @field:SerializedName("endDate")
    val endDate: String,

    @field:SerializedName("totalExpenses")
    val totalExpenses: String,

    @field:SerializedName("difference")
    val difference: String,

    @field:SerializedName("startDate")
    val startDate: String,

    @field:SerializedName("Date")
    val date: String,
):Parcelable
