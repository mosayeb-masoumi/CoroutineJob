package com.example.coroutinexample.retrofit_livedata_couroutine

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

private suspend fun <T> privateApiCall(emitter: RemoteErrorEmitter, responseFunction: suspend () -> T): T? {
    try{
        return responseFunction()
    }catch (e: Exception){
        withContext(Dispatchers.Main){
            e.printStackTrace()
            Log.e("ApiCalls", "Call error: ${e.localizedMessage}", e.cause)
            when(e){
                is HttpException -> {
                    val body = e.response()?.errorBody()
                    emitter.onError(getErrorMessage(body))
                }
                is TimeoutCancellationException -> emitter.onError(ErrorType.TIMEOUT)
                is IOException -> emitter.onError(ErrorType.NETWORK)
                else -> emitter.onError(ErrorType.UNKNOWN)
            }
        }
        return null
    }
}



fun <T> apiCall(emitter: RemoteErrorEmitter,responseFunction: suspend () -> T) : LiveData<T?> {
    return liveData {
        val respone = privateApiCall(emitter,{responseFunction()})
        emit(respone)
    }
}

private const val MESSAGE_KEY = "message"
private const val ERROR_KEY = "error"

fun getErrorMessage(responseBody: ResponseBody?): String {
    return try {
        val jsonObject = JSONObject(responseBody!!.string())
        when {
            jsonObject.has(MESSAGE_KEY) -> jsonObject.getString(MESSAGE_KEY)
            jsonObject.has(ERROR_KEY) -> jsonObject.getString(ERROR_KEY)
            else -> "Something wrong happened"
        }
    } catch (e: Exception) {
        "Something wrong happened"
    }
}


