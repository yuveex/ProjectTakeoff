package com.ubaya.projecttakeoff.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.ubaya.projecttakeoff.databinding.ArticleListItemBinding
import com.ubaya.projecttakeoff.model.Article
import java.lang.Exception

class ArticleListAdapter(val articleList: ArrayList<Article>)
    : RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder>(){
    class ArticleViewHolder(var binding: ArticleListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ArticleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        with(holder.binding){
            txtTitle.text = articleList[position].title
            txtAuthor.text = articleList[position].author_name
            txtSummary.text = articleList[position].summary

            val picasso = Picasso.Builder(holder.itemView.context)
            picasso.listener{picasso, uri, exception ->
                exception.printStackTrace()
            }
            picasso.build().load(articleList[position].image_url).into(imgArticleImage, object: Callback {
                override fun onSuccess(){
                    
                }

                override fun onError(e: Exception?) {
                    TODO("Not yet implemented")
                }
            })
        }
    }


}