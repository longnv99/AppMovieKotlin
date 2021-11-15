package com.example.appmoviekotlin.response.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Result (val name:String, val id:Int):Parcelable