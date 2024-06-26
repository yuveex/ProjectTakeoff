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
import com.ubaya.projecttakeoff.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.CoroutineContext

class UserViewModel(application: Application): AndroidViewModel(application), CoroutineScope {
    var userLD = MutableLiveData<User>()
    var loginStatusLD = MutableLiveData<Boolean>()
    var registerStatusLD = MutableLiveData<Boolean>()
    var updateStatusLD = MutableLiveData<Boolean>()
    var passUpdateStatusLD = MutableLiveData<Boolean>()

    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun login(username: String, password: String){
        launch {
            val db = buildDb(getApplication())
            var user = db.userDAO().login(username, password)

            if(user != null){
                //User would never be null in here. So (!!) is acceptable.
                userLD.postValue(user!!)
                loginStatusLD.postValue(true)
            }
            else{
                loginStatusLD.postValue(false)

            }
        }
    }

    fun readUser(userid: Int){
        launch {
            userLD.postValue(buildDb(getApplication()).userDAO().selectUser(userid))
        }

    }

    fun addUser(user: User){
        launch {
            buildDb(getApplication()).userDAO().insertAll(user)
        }
    }

    fun updateUser(user: User){
        launch {
            val db = buildDb(getApplication())
            val rowsUpdated = db.userDAO().updateUser(
                id = user.id,
                email = user.email,
                username = user.username,
                firstName = user.first_name,
                lastName = user.last_name,
                profilePicture = user.profile_picture
            )
            val success = rowsUpdated > 0
            updateStatusLD.postValue(success)
        }
    }

    fun updatePassword(id: Int, oldPassword: String, newPassword: String){
        launch {
            val db = buildDb(getApplication())
            val rowsUpdated = db.userDAO().updatePassword(id, oldPassword, newPassword)
            val success = rowsUpdated > 0
            passUpdateStatusLD.postValue(success)
        }
    }

    fun deleteUser(user: User){
        launch {
            buildDb(getApplication()).userDAO().deleteUser(user)
        }
    }

//    val TAG = "volleyUserTag"
//    private var queue: RequestQueue? = null

//    fun fetch(): User? {
//        return userLD.value
//    }

//    fun login(username: String, password: String){
//        var status = "Failed"
//
//        queue = Volley.newRequestQueue(getApplication())
//        val url = "http://10.0.2.2/ANMP/ProjectTakeoff/login_user.php"
//        val stringRequest = object: StringRequest(Request.Method.POST, url,
//            Response.Listener
//            {
//                val sTypeUser = object: TypeToken<User>(){}.type
//                val response = JSONObject(it)
//                status = response.getString("status")
//
//                if(status == "Success"){
//                    val data = response.getJSONObject("data")
//                    val result = Gson().fromJson<User>(data.toString(), sTypeUser)
//                    userLD.value = result as User
//                    loginStatusLD.value = true
//
//                    Log.d("userdata", userLD.value.toString())
//                }
//                else{
//                    loginStatusLD.value = false
//                }
//
//                Log.d("userstatus", status)
//
//            },
//            Response.ErrorListener {
//                Log.e("userreadvolley", it.toString())
//            })
//        {
//            override fun getParams(): MutableMap<String, String>? {
//                val params = HashMap<String, String>()
//                params["username"] = username
//                params["password"] = password
//                return params
//            }
//        }
//
//        stringRequest.tag = TAG
//        queue?.add(stringRequest)
//    }
//
//    fun readUser(user_id: String){
//        queue = Volley.newRequestQueue(getApplication())
//        val url = "http://10.0.2.2/ANMP/ProjectTakeoff/read_user.php"
//        val stringRequest = object: StringRequest(Request.Method.POST, url,
//            Response.Listener
//            {
//                val sTypeUser = object: TypeToken<User>(){}.type
//                val response = JSONObject(it)
//
//                val data = response.getJSONObject("data")
//                val result = Gson().fromJson<User>(data.toString(), sTypeUser)
//                userLD.value = result as User
//
////                Log.d("userdata", userLD.value.toString())
//
//            },
//            Response.ErrorListener {
//                Log.e("userreadvolley", it.toString())
//            })
//        {
//            override fun getParams(): MutableMap<String, String>? {
//                val params = HashMap<String, String>()
//                params["user_id"] = user_id
//                return params
//            }
//        }
//
//        stringRequest.tag = TAG
//        queue?.add(stringRequest)
//    }
//
//    fun createUser(email: String, username: String, first_name: String,
//                   last_name: String, password: String, profile_picture: String){
//        queue = Volley.newRequestQueue(getApplication())
//        val url = "http://10.0.2.2/ANMP/ProjectTakeoff/create_user.php"
//        val stringRequest = object: StringRequest(Request.Method.POST, url,
//        {
//            val response = JSONObject(it)
//            val result = response.getString("result")
//            if(result == "OK"){
//                registerStatusLD.value = true
//            }
//            else{
//                registerStatusLD.value = false
//            }
//
//        },
//        {
//            Log.e("userregistervolley", it.toString())
//        }){
//            override fun getParams(): MutableMap<String, String>? {
//                val params = HashMap<String, String>()
//                params["email"] = email
//                params["username"] = username
//                params["first_name"] = first_name
//                params["last_name"] = last_name
//                params["password"] = password
//                params["profile_picture"] = profile_picture
//                return params
//            }
//        }
//
//        stringRequest.tag = TAG
//        queue?.add(stringRequest)
//    }
//
//    fun updateUser(email: String, username: String, first_name: String,
//                   last_name: String, profile_picture: String, user_id: String) {
//        queue = Volley.newRequestQueue(getApplication())
//        val url = "http://10.0.2.2/ANMP/ProjectTakeoff/update_user.php"
//        val stringRequest = object : StringRequest(Request.Method.POST, url,
//            {
//                val response = JSONObject(it)
//                val result = response.getString("result")
//                if (result == "OK") {
//                    updateStatusLD.value = true
//                    readUser(user_id)
//                } else {
//                    updateStatusLD.value = false
//                }
//            },
//            {
//                Log.e("userupdatervolley", it.toString())
//            }) {
//            override fun getParams(): MutableMap<String, String>? {
//                val params = HashMap<String, String>()
//                params["email"] = email
//                params["username"] = username
//                params["first_name"] = first_name
//                params["last_name"] = last_name
//                params["profile_picture"] = profile_picture
//                params["user_id"] = user_id
//                return params
//            }
//        }
//
//        stringRequest.tag = TAG
//        queue?.add(stringRequest)
//    }
//
//    fun changePassword(old_password: String, new_password: String, user_id: String) {
//        queue = Volley.newRequestQueue(getApplication())
//        val url = "http://10.0.2.2/ANMP/ProjectTakeoff/update_user_password.php"
//        val stringRequest = object : StringRequest(Request.Method.POST, url,
//            {
//                val response = JSONObject(it)
//                val result = response.getString("result")
//                if (result == "OK") {
//                    passUpdateStatusLD.value = true
//                    readUser(user_id)
//                } else {
//                    passUpdateStatusLD.value = false
//                }
//            },
//            {
//                Log.e("passupdatevolley", it.toString())
//            }) {
//            override fun getParams(): MutableMap<String, String>? {
//                val params = HashMap<String, String>()
//                params["old_password"] = old_password
//                params["new_password"] = new_password
//                params["user_id"] = user_id
//                return params
//            }
//        }
//
//        stringRequest.tag = TAG
//        queue?.add(stringRequest)
//    }

    fun clearLiveData(){
        userLD = MutableLiveData<User>()
        loginStatusLD = MutableLiveData<Boolean>()
        registerStatusLD = MutableLiveData<Boolean>()
        updateStatusLD = MutableLiveData<Boolean>()
        passUpdateStatusLD = MutableLiveData<Boolean>()
    }

    override fun onCleared() {
        super.onCleared()
//        queue?.cancelAll(TAG)
    }

}