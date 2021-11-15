package com.example.appmoviekotlin.response

import android.os.Parcelable
import com.example.appmoviekotlin.response.model.Movie
import kotlinx.android.parcel.Parcelize
import java.util.ArrayList

@Parcelize
data class MoviePopularResponse (val page:Int, val results: ArrayList<Movie>, val total_pages:Int, val total_results:Int) : Parcelable