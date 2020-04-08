package com.edenh.newsclient.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.edenh.newsclient.model.Article
import com.edenh.newsclient.network.response.ArticlesResponse
import com.edenh.newsclient.network.retrofit.ApiRequest
import com.edenh.newsclient.network.retrofit.RetrofitRequest
import com.edenh.newsclient.utils.ARTICLES_SOURCE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticlesRepository(private val articleDao: ArticleDao) {
    private val apiRequest = RetrofitRequest.retrofit.create(ApiRequest::class.java)

    fun getArticles(): LiveData<ArticlesResponse?> {
        val data: MediatorLiveData<ArticlesResponse?> = MediatorLiveData()
        val articles: LiveData<List<Article>> = articleDao.getArticles()
        data.addSource(articles) {
            if (it != null) {
                val response = ArticlesResponse("ok", it.size, it)
                data.postValue(response)
            }
        }
        fetchAndInsertArticles()

        return data
    }

    private fun fetchAndInsertArticles() {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            try {
                val articleResponse = apiRequest.getArticles(ARTICLES_SOURCE)
                if (articleResponse != null) {
                    insertToDb(articleResponse.articles)
                }
            } catch (throwable: Throwable) {
                Log.e("EDEN", throwable.toString())
            }
        }
    }

    private suspend fun insertToDb(articles: List<Article>?) {
        articles?.let {
            articleDao.insert(it)
        }
    }

}