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
import com.ubaya.projecttakeoff.model.User
import org.json.JSONObject

class UserViewModel(application: Application): AndroidViewModel(application) {
    val userLD = MutableLiveData<User>()
    val loginStatusLD = MutableLiveData<Boolean>()

    val TAG = "volleyUserTag"
    private var queue: RequestQueue? = null

    fun login(username: String, password: String){
        var status = "Failed"

        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/ANMP/ProjectTakeoff/login_user.php"
        val stringRequest = object: StringRequest(Request.Method.POST, url,
            Response.Listener
            {
                val sTypeUser = object: TypeToken<User>(){}.type
                val response = JSONObject(it)
                status = response.getString("status")

                if(status == "Success"){
                    val data = response.getJSONObject("data")
                    val result = Gson().fromJson<User>(data.toString(), sTypeUser)
                    userLD.value = result as User
                    loginStatusLD.value = true

                    Log.d("userdata", data.toString())
                }
                else{
                    loginStatusLD.value = false
                }

                Log.d("userstatus", status)

            },
            Response.ErrorListener {
                Log.e("userreadvolley", it.toString())
            })
        {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["username"] = username
                params["password"] = password
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

}