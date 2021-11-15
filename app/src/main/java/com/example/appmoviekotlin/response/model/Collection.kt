package com.example.appmoviekotlin.response.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Collection (val id:Int, val name:String, val poster_path:String, val backdrop_path:String):Parcelable