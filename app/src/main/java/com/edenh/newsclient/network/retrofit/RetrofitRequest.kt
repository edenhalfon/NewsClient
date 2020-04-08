package com.edenh.newsclient.network.retrofit

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitRequest {

    val retrofit: Retrofit
    private const val BASE_URL = "https://newsapi.org/"
    private const val API_KEY_PARAMETER = "apikey"
    private const val API_KEY = "12376de7cad742609e068908bd8f092f"

    init {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor {
            val request = it.request()
            val requestBuilder = request.url().newBuilder().addQueryParameter(API_KEY_PARAMETER, API_KEY)

            val response = request.newBuilder().url(requestBuilder.build()).build()

            Log.d("EDEN", response.url().toString())

            it.proceed(response)
        }

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}