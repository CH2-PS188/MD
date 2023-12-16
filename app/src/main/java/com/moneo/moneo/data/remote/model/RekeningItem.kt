package com.moneo.moneo.data.remote.model

import com.google.gson.annotations.SerializedName

data class RekeningsItem(

    @field:SerializedName("id_account")
    val idAccount: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("saldo")
    val saldo: String? = null,

    @field:SerializedName("judul")
    val judul: String? = null
)