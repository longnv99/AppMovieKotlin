package com.example.appmoviekotlin.response.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Companies(val id:Int, val logo_path:String, val name:String, val origin_country:String):Parcelable