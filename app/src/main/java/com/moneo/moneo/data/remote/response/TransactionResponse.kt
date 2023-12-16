package com.moneo.moneo.data.remote.response

import com.google.gson.annotations.SerializedName
import com.moneo.moneo.data.remote.model.TransactionItem

data class TransactionResponse(

	val data: List<TransactionItem>
)