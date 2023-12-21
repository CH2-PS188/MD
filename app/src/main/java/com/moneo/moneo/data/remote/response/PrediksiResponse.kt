package com.moneo.moneo.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class PrediksiResponse(

	@field:SerializedName("perbandingan")
	val perbandingan: Perbandingan
)

@Parcelize
data class Perbandingan(

	@field:SerializedName("totalPemasukanINR")
	val totalPemasukanINR: Int,

	@field:SerializedName("tanggalPrediksi")
	val tanggalPrediksi: String,

	@field:SerializedName("totalPemasukanIDR")
	val totalPemasukanIDR: Int,

	@field:SerializedName("accuracy")
	val accuracy: String,

	@field:SerializedName("risk")
	val risk: Int,

	@field:SerializedName("totalWaktuLoading")
	val totalWaktuLoading: String
) : Parcelable
