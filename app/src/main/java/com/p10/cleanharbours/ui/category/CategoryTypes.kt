package com.p10.cleanharbours.ui.category

import com.google.gson.annotations.SerializedName

data class CategoryTypes(

	@field:SerializedName("Response")
	val response: List<ResponseItem?>? = null
)

data class ResponseItem(

	@field:SerializedName("type")
	val type: String? = null
)
