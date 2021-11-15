package com.example.appmoviekotlin.listener

import android.view.View
import com.example.appmoviekotlin.response.model.Movie

interface MovieListener {
    fun onMovieClick(view: View, movie : Movie)
}