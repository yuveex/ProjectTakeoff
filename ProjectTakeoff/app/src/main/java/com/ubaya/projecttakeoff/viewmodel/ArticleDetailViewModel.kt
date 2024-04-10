package com.ubaya.projecttakeoff.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubaya.projecttakeoff.model.Article
import org.json.JSONArray

class ArticleDetailViewModel(application: Application): AndroidViewModel(application){
    val articleLD = MutableLiveData<Article>()
    val articleContentLD = MutableLiveData<ArrayList<String>>()

    val TAG = "volleyArticleTag"
    private var queue: RequestQueue? = null

    fun refresh(articleId: String){
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/ANMP/ProjectTakeoff/read_article_detail.php"
        val stringRequest = object: StringRequest(Request.Method.POST, url, Response.Listener{
            val sTypeArticle = object: TypeToken<Article>(){}.type
            val jsonArray = JSONArray(it)
            val jsonObject = jsonArray.getJSONObject(0)
            val result = Gson().fromJson<Article>(jsonObject.toString(), sTypeArticle)
            articleLD.value = result as Article
            Log.d("articlereadresult", result.toString())

            articleContentLD.value = paginateContent(result.content, 1)
        },
        Response.ErrorListener{
            Log.e("articlereadvolley", it.toString())
        })
        {
            override fun getParams(): MutableMap<String, String>{
                val params = HashMap<String, String>()
                params["id"] = articleId
                return params
            }
        }
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }

    //Content of the article, with each paragraph split with "\n\n", pageSize is the number of Paragraph in a page
    private fun paginateContent(content: String, pageSize: Int): ArrayList<String>{
        var pages = arrayListOf<String>()

        val paragraphs = content.split("\n\n")

        var currentPage = ""
        var currentPageLength = 0

        for(paragraph in paragraphs){

            //If the current page reached the page limit, then store the current page and add new page.
            if(currentPageLength >= pageSize){
                pages.add(currentPage)
                currentPage = ""
                currentPageLength = 0
            }

            currentPage += (paragraph + "\n\n")
            currentPageLength += 1
        }

        if(currentPage != ""){
            pages.add(currentPage)
        }
        return pages
    }
}