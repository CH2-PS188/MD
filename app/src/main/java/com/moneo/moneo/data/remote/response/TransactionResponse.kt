package com.moneo.moneo.data.remote.response

import com.google.gson.annotations.SerializedName
import com.moneo.moneo.data.local.transaction.Transaction

data class TransactionResponse(

	@field:SerializedName("data")
	val data: List<DataItem>
)

data class DataItem(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("total")
	val total: String,

	@field:SerializedName("id_account")
	val idAccount: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("account")
	val account: String
)

fun Transaction.toDataItem(): DataItem {
	return DataItem(
		date = this.date.toString(),
		total = this.total.toString(),
		idAccount = this.idAccount.toString(),
		description = this.description.toString(),
		id = this.id,
		title = this.title.toString(),
		category = this.category.toString(),
		type = this.type.toString(),
		account = this.rekening.toString()
	)
}