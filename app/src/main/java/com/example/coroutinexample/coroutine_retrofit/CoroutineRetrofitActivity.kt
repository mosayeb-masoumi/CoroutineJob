package com.example.coroutinexample.coroutine_retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.coroutinexample.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory




class CoroutineRetrofitActivity : AppCompatActivity() {

    private val BASE_URL = "https://jsonplaceholder.typicode.com/"
    private var TAG: String ="RetrofitActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine_retrofit)


        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApi::class.java)



        /*********************** old method ********************/
        //old method
        api.getComments().enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if(response.isSuccessful){
                    response.body()?.let {
                        for (comment in it){
//                          Log.d(TAG ,comment.toString())
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                Log.d(TAG , "Error: $t")
            }
        })





        /*********************** call api by coroutine method ********************/

        GlobalScope.launch(Dispatchers.IO){

            val response = api.getCommentsByCoroutine()
            if(response.isSuccessful){
                for (comment in response.body()!!){
                    Log.d(TAG ,comment.toString())
                }
            }
        }


    }
}