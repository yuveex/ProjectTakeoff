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
import org.json.JSONArray
import org.json.JSONObject

class UserViewModel(application: Application): AndroidViewModel(application) {
    var userLD = MutableLiveData<User>()
    var loginStatusLD = MutableLiveData<Boolean>()
    var registerStatusLD = MutableLiveData<Boolean>()
    var updateStatusLD = MutableLiveData<Boolean>()

    val TAG = "volleyUserTag"
    private var queue: RequestQueue? = null

    fun fetch(): User? {
        return userLD.value
    }

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

                    Log.d("userdata", userLD.value.toString())
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

    fun readUser(user_id: String){
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/ANMP/ProjectTakeoff/read_user.php"
        val stringRequest = object: StringRequest(Request.Method.POST, url,
            Response.Listener
            {
                val sTypeUser = object: TypeToken<User>(){}.type
                val response = JSONObject(it)

                val data = response.getJSONObject("data")
                val result = Gson().fromJson<User>(data.toString(), sTypeUser)
                userLD.value = result as User

//                Log.d("userdata", userLD.value.toString())

            },
            Response.ErrorListener {
                Log.e("userreadvolley", it.toString())
            })
        {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["user_id"] = user_id
                return params
            }
        }

        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    fun createUser(email: String, username: String, first_name: String,
                   last_name: String, password: String, profile_picture: String){
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/ANMP/ProjectTakeoff/create_user.php"
        val stringRequest = object: StringRequest(Request.Method.POST, url,
        {
            val response = JSONObject(it)
            val result = response.getString("result")
            if(result == "OK"){
                registerStatusLD.value = true
            }
            else{
                registerStatusLD.value = false
            }

        },
        {
            Log.e("userregistervolley", it.toString())
        }){
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["email"] = email
                params["username"] = username
                params["first_name"] = first_name
                params["last_name"] = last_name
                params["password"] = password
                params["profile_picture"] = profile_picture
                return params
            }
        }

        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    fun updateUser(email: String, username: String, first_name: String,
                   last_name: String, profile_picture: String, user_id: String) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/ANMP/ProjectTakeoff/update_user.php"
        val stringRequest = object : StringRequest(Request.Method.POST, url,
            {
                val response = JSONObject(it)
                val result = response.getString("result")
                if (result == "OK") {
                    updateStatusLD.value = true
                    readUser(user_id)
                } else {
                    updateStatusLD.value = false
                }
            },
            {
                Log.e("userupdatervolley", it.toString())
            }) {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["email"] = email
                params["username"] = username
                params["first_name"] = first_name
                params["last_name"] = last_name
                params["profile_picture"] = profile_picture
                params["user_id"] = user_id
                return params
            }
        }

        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    fun clearLiveData(){
        userLD = MutableLiveData<User>()
        loginStatusLD = MutableLiveData<Boolean>()
        registerStatusLD = MutableLiveData<Boolean>()
        updateStatusLD = MutableLiveData<Boolean>()
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }

}