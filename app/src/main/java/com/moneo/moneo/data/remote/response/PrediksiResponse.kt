package com.moneo.moneo.data.remote.response

import com.google.gson.annotations.SerializedName
import com.moneo.moneo.data.remote.model.PrediksiItem

data class PrediksiResponse(

	@field:SerializedName("perbandingan")
	val perbandingan: PrediksiItem
)

