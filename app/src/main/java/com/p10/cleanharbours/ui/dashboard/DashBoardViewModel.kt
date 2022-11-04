package com.p10.cleanharbours.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.p10.cleanharbours.retrofit.ApiInterface
import com.p10.cleanharbours.retrofit.RetrofitInstance
import com.p10.cleanharbours.ui.category.WasteCategory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashBoardViewModel: ViewModel() {

    private var _dashboardResponse = MutableLiveData<DashboardResponseModel>()
    val dashboardResponse: LiveData<DashboardResponseModel> = _dashboardResponse

    fun init(tokenKey: String){
        dashBoardContentApi(tokenKey)
    }

    private fun dashBoardContentApi(token : String) {
        try {
            val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
            val map: MutableMap<String, String> = HashMap()
            map["Content-Type"] = "application/json"
            map["x-auth-token"] = token
            retIn.getDashBoardContent(map).enqueue(object : Callback<DashboardResponseModel> {
                override fun onFailure(call: Call<DashboardResponseModel>, t: Throwable) {

                }
                override fun onResponse(call: Call<DashboardResponseModel>, response: Response<DashboardResponseModel>) {
                    if (response.code() == 200) {
                        _dashboardResponse.postValue(response.body())
                    }
                }
            })
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

}