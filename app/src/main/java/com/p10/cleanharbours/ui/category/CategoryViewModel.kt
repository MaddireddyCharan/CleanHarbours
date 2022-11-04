package com.p10.cleanharbours.ui.category

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.p10.cleanharbours.retrofit.ApiInterface
import com.p10.cleanharbours.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel: ViewModel() {

    private var _categoryList = MutableLiveData<List<WasteCategory>>()
    val categoryList: LiveData<List<WasteCategory>> = _categoryList

    fun init(tokenKey: String){
        categoryListApi(tokenKey)
    }

    private fun categoryListApi(tokenKey: String) {
        try {
            val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
            val map: MutableMap<String, String> = HashMap()
            map["Content-Type"] = "application/json"
            map["x-auth-token"] = tokenKey
            retIn.getTypes(map).enqueue(object : Callback<List<ResponseItem>> {
                override fun onFailure(call: Call<List<ResponseItem>>, t: Throwable) {
                    Log.d("type failure","get types is failing")
                }
                override fun onResponse(call: Call<List<ResponseItem>>, response: Response<List<ResponseItem>>) {
                    if (response.code() == 200) {
                        Log.d("type failure","get types is success")
                        val categoryListObj: MutableList<WasteCategory> = mutableListOf()
                        response.body()?.forEach {
                            categoryListObj.add(WasteCategory(it.type.orEmpty(), false))
                        }
                        _categoryList.postValue(categoryListObj)
                    }
                }
            })
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    fun formCommaSeparatedString(selectedCategories: List<WasteCategory>): String{
        var categoryNames = ""
        selectedCategories.forEach {
            if(categoryNames.isEmpty())
                categoryNames = it.category
            else
                categoryNames = categoryNames + "," + it.category
        }
        return categoryNames
    }
}