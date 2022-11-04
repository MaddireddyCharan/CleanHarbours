package com.p10.cleanharbours.ui.dashboard

import com.google.gson.annotations.SerializedName

data class DashboardResponseModel(

	@field:SerializedName("firstName")
	val firstName: String? = null,

	@field:SerializedName("lastName")
	val lastName: String? = null,

	@field:SerializedName("wasteData")
	val wasteData: List<WasteDataItem?>? = null
)

data class WasteDataItem(

	@field:SerializedName("itemsPickedUp")
	val itemsPickedUp: Int? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("paymentRecieved")
	val paymentRecieved: Int? = null
)
