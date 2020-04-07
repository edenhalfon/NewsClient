package com.edenh.newsclient.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.edenh.newsclient.network.response.ArticlesResponse
import com.edenh.newsclient.network.retrofit.ApiRequest
import com.edenh.newsclient.network.retrofit.RetrofitRequest
import com.edenh.newsclient.utils.API_KEY
import com.edenh.newsclient.utils.GENERAL_FAILURE
import com.edenh.newsclient.utils.NETWORK_FAILURE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticlesRepository {
    private val TAG: String = javaClass.simpleName
    private val apiRequest = RetrofitRequest.retrofit.create(ApiRequest::class.java)


    fun getArticles(source: String?): LiveData<ArticlesResponse?> {
        val data: MutableLiveData<ArticlesResponse?> = MutableLiveData()
        apiRequest.getArticles(source, API_KEY).enqueue(object : Callback<ArticlesResponse?> {
                override fun onResponse(
                    call: Call<ArticlesResponse?>,
                    response: Response<ArticlesResponse?>
                ) {
                    Log.d(TAG, "onResponse: $response")
                    if (response.body() != null) {
                        data.postValue(response.body())
                    } else {
                        val failureResponse = ArticlesResponse(GENERAL_FAILURE)
                        data.postValue(failureResponse)
                    }
                }

                override fun onFailure(
                    call: Call<ArticlesResponse?>,
                    t: Throwable
                ) {
                    val failureResponse = ArticlesResponse(NETWORK_FAILURE)
                    data.postValue(failureResponse)
                }
            })
        return data
    }

}