package com.p10.cleanharbours.ui.login

import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.p10.cleanharbours.R
import com.p10.cleanharbours.data.AppRepository
import com.p10.cleanharbours.data.model.LogInResponse
import com.p10.cleanharbours.data.model.LoggedInUser
import com.p10.cleanharbours.data.model.SignInBody
import com.p10.cleanharbours.retrofit.ApiInterface
import com.p10.cleanharbours.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val loginRepository: AppRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        loginApiCall(username, password)//loginRepository.login(username, password)
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    private fun loginApiCall(username: String, password: String) {
        try {
            val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
            val signInInfo = SignInBody(username, password)
            var user : LoggedInUser? = null
            val map: MutableMap<String, String> = HashMap()
            map["Content-Type"] = "application/json"
            retIn.signin(signInInfo, map).enqueue(object : Callback<LogInResponse> {
                override fun onFailure(call: Call<LogInResponse>, t: Throwable) {
                    _loginResult.value = LoginResult(error = R.string.login_failed)
                }
                override fun onResponse(call: Call<LogInResponse>, response: Response<LogInResponse>) {
                    if (response.code() == 200) {
                        if(response.body()?.username?.isEmpty() == true){
                            _loginResult.value = LoginResult(error = R.string.login_failed)
                        }
                        else
                        {
                            _loginResult.value =
                                LoginResult(success = LoggedInUserView(displayName = response.body()?.username.orEmpty(),
                                    response.headers()["X-Auth-Token"].orEmpty()))
                        }
                    }
                }
            })
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

}