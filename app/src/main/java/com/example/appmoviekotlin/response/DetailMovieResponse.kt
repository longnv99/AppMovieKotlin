package com.example.appmoviekotlin.response

import android.os.Parcelable
import com.example.appmoviekotlin.response.model.*
import com.example.appmoviekotlin.response.model.Collection
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailMovieResponse(val adult:Boolean, val backdrop_path:String, val belongs_to_collection:Collection
                               , val budget:Double, val genres:List<Genner>, val homepage:String
                               , val id:Int, val imdb_id:String, val original_language:String
                               , val original_title:String, val overview:String
                               , val popularity:Float, val poster_path:String, val production_companies:List<Companies>
                               , val production_countries:List<Countries>
                               , val release_date:String, val revenue:Double
                               , val runtime:Int, val spoken_languages:List<Languages>
                               , val status:String, val tagline:String, val title:String
                               , val video:Boolean, val vote_average:Float, val vote_count:Int) : Parcelable