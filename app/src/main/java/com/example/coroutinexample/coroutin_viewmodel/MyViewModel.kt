package com.example.coroutinexample.coroutin_viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class MyViewModel(context: Context) : ViewModel() {

    var context : Context? = context
    var mutableLiveData: MutableLiveData<List<String>> = MutableLiveData()


    fun getList(): LiveData<List<String>> {

        viewModelScope.launch {

            try {
                // coroutineScope is needed, else in case of any network error, it will crash
                coroutineScope {
                                //using async for parallel task
                    val result = withContext(Dispatchers.IO) { fetchLiatData() }

                    mutableLiveData.postValue(result)
                }
            } catch (e: Exception) {
                //send empety list
                val list = arrayListOf<String>()
                mutableLiveData.postValue(list)
            }


        }

        return mutableLiveData
    }



    private suspend fun fetchLiatData() : List<String>{
        delay(3000)
        val list = arrayListOf<String>()
        list.add("reza")
        list.add("hassan")
        list.add("gholi")
        return list
    }
}