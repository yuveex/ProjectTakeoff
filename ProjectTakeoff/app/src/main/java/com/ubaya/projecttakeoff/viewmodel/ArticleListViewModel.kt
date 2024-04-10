package com.ubaya.projecttakeoff.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubaya.projecttakeoff.model.Article

class ArticleListViewModel(application: Application): AndroidViewModel(application) {
    val articlesLD = MutableLiveData<ArrayList<Article>>()

    val TAG = "volleyArticleListTag"
    private var queue: RequestQueue? = null

    fun refresh(){
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/ANMP/ProjectTakeoff/read_articles.php"
        val stringRequest = StringRequest(Request.Method.GET, url, {
            val sTypeArticles = object: TypeToken<List<Article>>(){}.type
            val result = Gson().fromJson<List<Article>>(it, sTypeArticles)
            articlesLD.value = result as ArrayList<Article>
//            Log.d("articlesresult", result.toString())
        },
        {
            Log.e("articlesvolley", it.toString())
        })

        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}