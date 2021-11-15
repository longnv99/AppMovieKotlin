package com.example.appmoviekotlin.response.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Languages (val english_name:String, val iso_639_1:String, val name:String):Parcelable