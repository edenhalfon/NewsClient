package com.edenh.newsclient.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.edenh.newsclient.model.Article
import com.edenh.newsclient.network.response.ArticlesResponse
import com.edenh.newsclient.repository.ArticlesRepository
import com.edenh.newsclient.repository.ArticlesRoomDatabase
import com.edenh.newsclient.utils.ARTICLES_SOURCE
import com.edenh.newsclient.utils.GENERAL_FAILURE
import com.edenh.newsclient.utils.NETWORK_FAILURE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticlesViewModel(application: Application) : AndroidViewModel(application) {

    private val articleRepository: ArticlesRepository
    private val errorLiveData: MutableLiveData<String> = MutableLiveData()
    private val articlesLiveData: MediatorLiveData<List<Article>> = MediatorLiveData()

    init {
        val articleDao = ArticlesRoomDatabase.getDatabase(application).articleDao()
        articleRepository = ArticlesRepository(articleDao)
    }

    fun getArticlesLiveData(): LiveData<List<Article>> {
        return articlesLiveData
    }

    fun getErrorLiveData(): LiveData<String?>? {
        return errorLiveData
    }

    fun fetchArticles() {
        val articlesResponse: LiveData<ArticlesResponse?> =
            articleRepository.getArticles()
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