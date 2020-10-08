package com.example.daggerhilt.retrofit

import retrofit2.http.GET

interface BlogRetrofit {

    @GET("blogs")
    suspend fun getData(): List<BlogNetworkEntity>

}