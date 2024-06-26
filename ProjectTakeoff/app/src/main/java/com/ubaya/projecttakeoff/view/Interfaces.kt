package com.ubaya.projecttakeoff.view

import android.view.View

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