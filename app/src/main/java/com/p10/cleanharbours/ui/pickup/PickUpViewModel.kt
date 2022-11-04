package com.p10.cleanharbours.ui.pickup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.p10.cleanharbours.retrofit.ApiInterface
import com.p10.cleanharbours.retrofit.RetrofitInstance
import com.p10.cleanharbours.ui.schedules_pickups.PickUps
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PickUpViewModel: ViewModel() {
    lateinit var token: String
    private var _pickUpsLiveData = MutableLiveData<PickUps>()
    val pickUpsLiveData: LiveData<PickUps> = _pickUpsLiveData

    fun init(tokenKey: String){
        token = tokenKey
    }

    fun formDataModel(selectedCategories: String, city: String, street: String, pinCode: String, otherInfo: String,  ){
        val list = selectedCategories.split(",")
        val address = Address(otherInfo, city, street, pinCode)
        val pickUpModel = PickUpDataModel(address, list)
        pickUpApiCall(pickUpModel)
    }

    private fun pickUpApiCall(pickUpDataModel: PickUpDataModel) {
        try {
            val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)

            val map: MutableMap<String, String> = HashMap()
            map["Content-Type"] = "application/json"
            map["x-auth-token"] = token
            retIn.pickUp(pickUpDataModel, map).enqueue(object : Callback<PickUps> {
                override fun onFailure(call: Call<PickUps>, t: Throwable) {

                }
                override fun onResponse(call: Call<PickUps>, response: Response<PickUps>) {
                    if (response.code() == 200) {
                        _pickUpsLiveData.postValue(response.body())
                    }
                }
            })
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

}