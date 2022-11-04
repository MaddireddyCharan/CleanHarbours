package com.p10.cleanharbours.ui.schedules_pickups

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.p10.cleanharbours.retrofit.ApiInterface
import com.p10.cleanharbours.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScheduledPickUpsViewModel: ViewModel() {
    private var _pickUpList = MutableLiveData<PickUps>()
    val pickUpList: LiveData<PickUps> = _pickUpList

    fun init(tokenKey: String){
        categoryListApi(tokenKey)
    }

    private fun categoryListApi(tokenKey: String) {
        try {
            val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
            val map: MutableMap<String, String> = HashMap()
            map["Content-Type"] = "application/json"
            map["x-auth-token"] = tokenKey
            retIn.pickUPs(map).enqueue(object : Callback<PickUps> {
                override fun onFailure(call: Call<PickUps>, t: Throwable) {
                    Log.d("type failure", "get types is failing")
                }

                override fun onResponse(call: Call<PickUps>, response: Response<PickUps>) {
                    if (response.code() == 200) {
                        Log.d("type failure", "get types is success")
                        _pickUpList.postValue(response.body())
                    }
                }
            })
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}