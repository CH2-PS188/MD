package com.moneo.moneo.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RekeningsItem(

//    @field:SerializedName("id_account")
//    val idAccount: String,
//
//    @field:SerializedName("id")
//    val id: Int,

    @field:SerializedName("saldo")
    val saldo: String,

    @field:SerializedName("judul")
    val judul: String
): Parcelable