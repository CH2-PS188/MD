package com.moneo.moneo.data.network.model

import com.google.gson.annotations.SerializedName

data class AccountResponse(

	@field:SerializedName("rekenings")
	val rekenings: List<RekeningsItem?>? = null,

	@field:SerializedName("totalSaldo")
	val totalSaldo: String? = null
)

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
