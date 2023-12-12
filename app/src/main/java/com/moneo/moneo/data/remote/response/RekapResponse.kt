package com.moneo.moneo.data.remote.response


import com.google.gson.annotations.SerializedName

data class RekapResponse(

	@field:SerializedName("listRekap")
	val listRekap: List<ListRekapItem>

)
