package com.moneo.moneo.data.remote.response

import com.google.gson.annotations.SerializedName

data class RekapResponse(

	@field:SerializedName("summary")
	val summary: Summary? = null
)

data class Summary(

	@field:SerializedName("totalIncome")
	val totalIncome: String? = null,

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("endDate")
	val endDate: String? = null,

	@field:SerializedName("totalExpenses")
	val totalExpenses: String? = null,

	@field:SerializedName("difference")
	val difference: String? = null,

	@field:SerializedName("startDate")
	val startDate: String? = null,

	@field:SerializedName("Date")
	val date: String? = null
)
