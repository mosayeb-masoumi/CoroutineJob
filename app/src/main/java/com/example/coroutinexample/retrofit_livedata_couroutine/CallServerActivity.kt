package com.example.coroutinexample.retrofit_livedata_couroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.example.coroutinexample.R



class CallServerActivity : AppCompatActivity() ,RemoteErrorEmitter {



    val webService : webService = RetrofitBuilder.api

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_server)



//        var thePosts = apiCall<MutableList<Post>>(this , {webService.getPosts()}).observe(
//            this, Observer {
//                if(it != null){
//                    for (item in it){
//                        Log.d("Here ", item.title)
//                    }
//
//                }
//            }
//        )

        var thePosts = apiCall<MutableList<Post>>(this) { webService.getPosts() }.observe(
            this, Observer {
                if(it != null){
                    for (item in it){
                        Log.d("Here ", item.title)
                    }

                }
            }
        )




    }



    override fun onError(msg: String) {
        Log.i("" ,"")
    }

    override fun onError(errorType: ErrorType) {
        Log.i("" ,"")
    }
}