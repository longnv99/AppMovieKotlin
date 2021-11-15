package com.example.appmoviekotlin.response

import android.os.Parcelable
import com.example.appmoviekotlin.response.model.Result
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchResultResponse (val page:Int, val results:ArrayList<Result>, val total_pages:Int, val total_results:Int):Parcelable