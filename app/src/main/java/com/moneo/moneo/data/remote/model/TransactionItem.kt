package com.moneo.moneo.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionItem(

    @field:SerializedName("date")
    val date: String,

    @field:SerializedName("total")
    val total: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("category")
    val category: String,

    @field:SerializedName("account")
    val account: String
): Parcelable
