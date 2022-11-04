package com.p10.cleanharbours.data

import com.p10.cleanharbours.data.model.LogInResponse
import com.p10.cleanharbours.data.model.LoggedInUser
import com.p10.cleanharbours.data.model.SignInBody
import com.p10.cleanharbours.data.model.UserBody
import com.p10.cleanharbours.retrofit.ApiInterface
import com.p10.cleanharbours.retrofit.RetrofitInstance
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class DataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
            val signInInfo = SignInBody(username, password)
            val map: MutableMap<String, String> = HashMap()
            map["Content-Type"] = "application/json"
            retIn.signin(signInInfo, map).enqueue(object : Callback<LogInResponse> {
                override fun onFailure(call: Call<LogInResponse>, t: Throwable) {

                }
                override fun onResponse(call: Call<LogInResponse>, response: Response<LogInResponse>) {
                    if (response.code() == 200) {
                        var token = response.headers().get("X-Auth-Token")
                    } else {

                    }
                }
            })
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error("")
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }

    private fun signup(userName: String, password: String, confirmPassword: String){
        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        val registerInfo = UserBody(userName, password, confirmPassword)
        val map: MutableMap<String, String> = HashMap()
        map["Content-Type"] = "application/json"
        retIn.registerUser(registerInfo, map).enqueue(object :
            Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 201) {

                }
                else{

                }
            }
        })
    }
}