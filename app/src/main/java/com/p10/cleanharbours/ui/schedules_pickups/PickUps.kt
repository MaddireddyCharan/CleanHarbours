package com.p10.cleanharbours.ui.schedules_pickups

import com.google.gson.annotations.SerializedName

data class PickUps(

	@field:SerializedName("pickups")
	val pickups: List<PickupsItem?>? = null
)

data class Address(

	@field:SerializedName("otherInfo")
	val otherInfo: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("street")
	val street: String? = null,

	@field:SerializedName("pinCode")
	val pinCode: String? = null
)

data class PickupsItem(

	@field:SerializedName("address")
	val address: Address? = null,

	@field:SerializedName("vendor")
	val vendor: String? = null,

	@field:SerializedName("time")
	val time: Long? = null,

	@field:SerializedName("type")
	val type: String? = null
)
