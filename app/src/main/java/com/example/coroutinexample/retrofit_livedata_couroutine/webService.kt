package com.example.coroutinexample.retrofit_livedata_couroutine

import retrofit2.http.GET

interface webService {

    @GET("posts")
    suspend fun getPosts(): MutableList<Post>
}