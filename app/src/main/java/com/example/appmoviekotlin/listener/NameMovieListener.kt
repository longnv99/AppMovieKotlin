package com.example.appmoviekotlin.listener

import android.view.View
import com.example.appmoviekotlin.response.model.Result

interface NameMovieListener {
    fun onNameMovieClick(view: View, name : Result)
}