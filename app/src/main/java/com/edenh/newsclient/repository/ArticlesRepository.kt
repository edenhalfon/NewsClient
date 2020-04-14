package com.edenh.newsclient.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.edenh.newsclient.model.Article
import com.edenh.newsclient.network.response.ArticlesResponse
import com.edenh.newsclient.network.retrofit.ApiRequest
import com.edenh.newsclient.network.retrofit.RetrofitRequest
import com.edenh.newsclient.utils.ARTICLES_SOURCE
import com.edenh.newsclient.utils.GENERAL_FAILURE
import com.edenh.newsclient.utils.NETWORK_FAILURE
import java.net.UnknownHostException

class ArticlesRepository(private val articleDao: ArticleDao) {
    private val TAG = "ArticlesRepository"

    private val apiRequest = RetrofitRequest.retrofit.create(ApiRequest::class.java)

    fun getArticles(): LiveData<ArticlesResponse?> {
        val data: MediatorLiveData<ArticlesResponse?> = MediatorLiveData()
        val articles: LiveData<List<Article>> = articleDao.getArticles()
        data.addSource(articles) {
            if (it != null) {
                Log.d(TAG, "DB DATA")
                val response = ArticlesResponse("ok", it.size, it)
                data.postValue(response)
            }
        }

        return data
    }


    suspend fun fetchAndInsertArticles(): LiveData<ArticlesResponse?>{
        Log.d(TAG, "fetching...")
        var articleResponse: ArticlesResponse?
        try {
            articleResponse = apiRequest.getArticles(ARTICLES_SOURCE)
            insertToDb(articleResponse?.articles)
            Log.d(TAG, "finished fetching and insert to db")
        } catch (network: UnknownHostException) {
            Log.e(TAG, "Error on the network")
            articleResponse = ArticlesResponse(NETWORK_FAILURE, 0, null)
        } catch (e: Exception) {
            Log.e(TAG, "General Error")
            articleResponse = ArticlesResponse(GENERAL_FAILURE, 0, null)
            e.printStackTrace()
        }

        Log.d(TAG, "liveData returned")

        return MutableLiveData(articleResponse)
    }

    private suspend fun insertToDb(articles: List<Article>?) {
        articles?.let {
            articleDao.insert(it)
        }
    }

}