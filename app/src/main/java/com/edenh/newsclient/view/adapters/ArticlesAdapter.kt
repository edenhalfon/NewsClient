package com.edenh.newsclient.view.adapters

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

class ArticlesAdapter(private val articlesData: List<Article>) :
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
        holder.authorAndPublishDate.text = holder.authorAndPublishDate.context
            .getString(R.string.author_date_template, article.author, article.publishDate)



        Glide.with(holder.coverImage.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(article.imageUrl)
            .into(holder.coverImage)
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