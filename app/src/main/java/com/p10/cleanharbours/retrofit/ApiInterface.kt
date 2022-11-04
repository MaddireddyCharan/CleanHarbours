package com.p10.cleanharbours.retrofit

import com.p10.cleanharbours.data.model.LogInResponse
import com.p10.cleanharbours.data.model.SignInBody
import com.p10.cleanharbours.data.model.UserBody
import com.p10.cleanharbours.ui.category.ResponseItem
import com.p10.cleanharbours.ui.dashboard.DashboardResponseModel
import com.p10.cleanharbours.ui.pickup.PickUpDataModel
import com.p10.cleanharbours.ui.schedules_pickups.PickUps
import okhttp3.ResponseBody
import retrofit2.http.*


interface ApiInterface {

    @POST("login")
    fun signin(@Body info: SignInBody, @HeaderMap headers: MutableMap<String, String>): retrofit2.Call<LogInResponse>

    @POST("signup")
    fun registerUser(
        @Body info: UserBody, @HeaderMap headers: MutableMap<String, String>
    ): retrofit2.Call<ResponseBody>

    @GET("types")
    fun getTypes( @HeaderMap headers: MutableMap<String, String>): retrofit2.Call<List<ResponseItem>>

    @GET("dashboard")
    fun getDashBoardContent(@HeaderMap headers: MutableMap<String, String>): retrofit2.Call<DashboardResponseModel>

    @POST("pickup")
    fun pickUp(@Body info: PickUpDataModel, @HeaderMap headers: MutableMap<String, String>): retrofit2.Call<PickUps>

    @GET("pickups")
    fun pickUPs(@HeaderMap headers: MutableMap<String, String>): retrofit2.Call<PickUps>


}