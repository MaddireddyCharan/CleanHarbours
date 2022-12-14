package com.p10.cleanharbours.data

import com.p10.cleanharbours.data.model.LoggedInUser

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: LoggedInUser?) : Result<T>()
    data class Error(val exception: String) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}