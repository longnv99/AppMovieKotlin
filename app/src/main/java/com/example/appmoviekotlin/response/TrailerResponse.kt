package com.example.appmoviekotlin.response

import android.os.Parcelable
import com.example.appmoviekotlin.response.model.Trailer
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TrailerResponse (val id:Int, val results:List<Trailer>) :Parcelable