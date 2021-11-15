package com.example.appmoviekotlin.response.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Countries(val iso_3166_1:String, val name:String):Parcelable