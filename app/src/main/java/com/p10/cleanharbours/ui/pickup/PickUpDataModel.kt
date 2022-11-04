package com.p10.cleanharbours.ui.pickup

import com.google.gson.annotations.SerializedName

data class PickUpDataModel(

	@field:SerializedName("address")
	val address: Address? = null,

	@field:SerializedName("pickups")
	val pickups: List<String?>? = null
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
