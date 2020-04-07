package com.edenh.newsclient.view.adapters

import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.edenh.newsclient.R
import com.edenh.newsclient.model.Article
import com.edenh.newsclient.utils.INTENT_ARTICLE_URL
import com.edenh.newsclient.view.ArticleActivity

class ArticlesAdapter(var articlesData: List<Article>) :
    RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {

    private val requestOptions: RequestOptions = RequestOptions()
        .placeholder(R.drawable.ic_place_holder)
        .error(R.drawable.ic_place_holder)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.article_item_layout, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articlesData[position]
        holder.title.text = article.title
        holder.description.text = article.description

        if (TextUtils.isEmpty(article.author)) {
            holder.authorAndPublishDate.text = article.publishedAt
        } else {
            holder.authorAndPublishDate.text = holder.authorAndPublishDate.context
                .getString(R.string.author_date_template, article.author, article.publishedAt)
        }

        Glide.with(holder.coverImage.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(article.urlToImage)
            .into(holder.coverImage)

        holder.itemView.setOnClickListener() {
            val articleIntent = Intent(holder.itemView.context, ArticleActivity::class.java)
            articleIntent.putExtra(INTENT_ARTICLE_URL, article.url);
            holder.itemView.context.startActivity(articleIntent)
        }
    }

    override fun getItemCount(): Int {
        return articlesData.size
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val coverImage: ImageView = itemView.findViewById(R.id.article_cover)
        val title: TextView = itemView.findViewById(R.id.article_title)
        val authorAndPublishDate: TextView = itemView.findViewById(R.id.article_details)
        val description: TextView = itemView.findViewById(R.id.article_description)

    }
}