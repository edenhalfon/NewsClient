package com.edenh.newsclient.network.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitRequest {

    val retrofit: Retrofit
    private const val BASE_URL = "https://newsapi.org/"

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}