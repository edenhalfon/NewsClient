package com.edenh.newsclient.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.edenh.newsclient.model.Article
import com.edenh.newsclient.network.response.ArticlesResponse
import com.edenh.newsclient.repository.ArticlesRepository
import com.edenh.newsclient.utils.ARTICLES_SOURCE
import com.edenh.newsclient.utils.GENERAL_FAILURE
import com.edenh.newsclient.utils.NETWORK_FAILURE

class ArticlesViewModel : ViewModel() {

    private val articleRepository = ArticlesRepository()

    private val errorLiveData: MutableLiveData<String> = MutableLiveData()
    private val articlesLiveData: MediatorLiveData<List<Article>> = MediatorLiveData()

    fun getArticlesLiveData(): LiveData<List<Article>> {
        return articlesLiveData
    }

    fun getErrorLiveData(): LiveData<String?>? {
        return errorLiveData
    }

    fun fetchArticles() {
        val articlesResponse: LiveData<ArticlesResponse?> =
            articleRepository.getArticles(ARTICLES_SOURCE)
        articlesLiveData.addSource(articlesResponse) {
            if (it != null) {
                val status: String = it.status.toString()
                if (NETWORK_FAILURE == status
                    || GENERAL_FAILURE == status
                ) {
                    errorLiveData.postValue(status)
                } else {
                    articlesLiveData.setValue(it.articles)
                }
            }
        }
    }
}