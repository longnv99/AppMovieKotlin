package com.example.appmoviekotlin.response

import android.os.Parcelable
import com.example.appmoviekotlin.response.model.Dates
import com.example.appmoviekotlin.response.model.Movie
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieUpComingResponse(val dates: Dates, val page:Int, val results: ArrayList<Movie>, val total_pages:Int, val total_results:Int) : Parcelable