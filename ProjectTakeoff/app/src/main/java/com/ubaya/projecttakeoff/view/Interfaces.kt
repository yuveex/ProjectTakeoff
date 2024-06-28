package com.ubaya.projecttakeoff.view

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imgUrl: String){
    imgUrl?.let {
        Picasso.get()
            .load(it)
            .into(view, object : Callback {
                override fun onSuccess() {
                }

                override fun onError(e: Exception?) {
                    Log.e("picasso_error", e.toString() + imgUrl)
                }
            })
    }
}
interface ButtonClickListener{
    fun onReadClick(view: View){

    }
}

interface PageButtonClickListener{
    fun onNextClick(view: View){

    }
    fun onPreviousClick(view: View){

    }
}

interface ProfileButtonClickListener{

    fun onUpdateClick(view: View){

    }

    fun onLogoutClick(view: View){

    }

    fun onChangePassClick(view: View){

    }
}

interface ChangePassButtonClickListener{
    fun onChangePassClick(view: View){

    }
}

interface LoginButtonClickListener{
    fun onLoginClick(view: View){

    }
    fun onRegisterClick(view: View){

    }
}

interface RegisterButtonClickListener{
    fun onRegisterClick(view: View){

    }
}