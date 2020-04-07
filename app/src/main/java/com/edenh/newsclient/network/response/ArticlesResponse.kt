package com.edenh.newsclient.network.response

import com.edenh.newsclient.model.Article
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ArticlesResponse(
    @SerializedName("status") @Expose val status: String? = null,
    @SerializedName("totalResults") @Expose val totalResults: Int? = null,
    @SerializedName("articles") @Expose val articles: List<Article>? = null
)