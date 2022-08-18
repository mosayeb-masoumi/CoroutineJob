package com.example.coroutinexample.coroutine_retrofit

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface MyApi {


    /********************** old way ***************/
    @GET("/comments")
    fun getComments(): Call<List<Comment>>

    @GET("/comments")
    suspend fun getCommentsByCoroutine(): Response<List<Comment>>
}