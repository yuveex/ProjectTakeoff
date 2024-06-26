package com.ubaya.projecttakeoff.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.ubaya.projecttakeoff.databinding.ArticleListItemBinding
import com.ubaya.projecttakeoff.model.Article
import java.lang.Exception

class ArticleListAdapter(val articleList: ArrayList<Article>)
    : RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder>(), ButtonClickListener{
    class ArticleViewHolder(var binding: ArticleListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ArticleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {

        holder.binding.article = articleList[position]
        holder.binding.readListener = this

        with(holder.binding){
//            txtTitle.text = articleList[position].title
//            txtAuthor.text = articleList[position].author_name
//            txtSummary.text = articleList[position].summary

//            btnRead.setOnClickListener {
//                val action = ArticleListFragmentDirections.actionArticleListFragmentToReadArticleFragment(articleList[position].id)
//                Navigation.findNavController(it).navigate(action)
//            }

            val picasso = Picasso.Builder(holder.itemView.context)
            picasso.listener{picasso, uri, exception ->
                exception.printStackTrace()
            }

            var imgUrl = "http://10.0.2.2/ANMP/ProjectTakeoff/images/" + (articleList[position].image_url)
            picasso.build().load(imgUrl).into(imgArticleImage, object: Callback {
                override fun onSuccess(){
                    
                }

                override fun onError(e: Exception?) {
                    Log.e("picasso_error", e.toString() + imgUrl)
                }
            })
            Log.d("picasso_url", imgUrl)
        }
    }

    override fun onReadClick(view: View) {
        val articleId = view.tag.toString().toInt()
        val action = ArticleListFragmentDirections.actionArticleListFragmentToReadArticleFragment(articleId)
        Navigation.findNavController(view).navigate(action)
    }

    fun updateArticleList(newArticleList: ArrayList<Article>){
        articleList.clear()
        articleList.addAll(newArticleList)
        notifyDataSetChanged()
    }


}