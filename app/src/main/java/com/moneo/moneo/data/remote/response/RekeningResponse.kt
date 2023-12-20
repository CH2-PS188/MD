package com.moneo.moneo.data.remote.response

import android.util.Log
import com.google.gson.annotations.SerializedName
import com.moneo.moneo.data.local.rekening.Rekening

data class RekeningResponse(

	@field:SerializedName("rekenings")
	val rekenings: List<RekeningsItem>,

	@field:SerializedName("totalSaldo")
	val totalSaldo: String
)

data class RekeningsItem(

	@field:SerializedName("id_account")
	val idAccount: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("saldo")
	val saldo: String,

	@field:SerializedName("judul")
	val judul: String
)

fun Rekening.toRekeningsItem(): RekeningsItem {
	return RekeningsItem(
		id = this.rekeningId,
		idAccount = this.idAccount.toString(),
		judul = this.name.toString(),
		saldo = this.balance.toString(),
	)

}
