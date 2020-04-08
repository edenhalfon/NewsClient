package com.edenh.newsclient.network.retrofit

import com.edenh.newsclient.network.response.ArticlesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequest {

    @GET("v2/everything/")
    suspend fun getArticles(
        @Query("sources") sources: String?
    ): ArticlesResponse?
}