package com.moneo.moneo.data.remote.response

import com.google.gson.annotations.SerializedName
import com.moneo.moneo.data.remote.model.RekeningsItem

data class RekeningResponse(

	@field:SerializedName("rekenings")
	val rekenings: List<RekeningsItem>,

	@field:SerializedName("totalSaldo")
	val totalSaldo: String
)

