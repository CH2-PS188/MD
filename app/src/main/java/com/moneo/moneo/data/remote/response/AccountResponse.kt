package com.moneo.moneo.data.remote.response

import com.google.gson.annotations.SerializedName
import com.moneo.moneo.data.remote.model.RekeningsItem

data class AccountResponse(

	@field:SerializedName("rekenings")
	val rekenings: List<RekeningsItem?>? = null,

	@field:SerializedName("totalSaldo")
	val totalSaldo: String? = null
)

