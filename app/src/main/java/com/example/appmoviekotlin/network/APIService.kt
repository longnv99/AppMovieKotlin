package com.example.appmoviekotlin.network

import com.example.appmoviekotlin.response.*
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET("3/movie/upcoming")
    fun getMovieUpComing(@Query("api_key") api_key:String,
                         @Query("language") language:String,
                         @Query("page") page:Int) : Observable<MovieUpComingResponse>

    @GET("3/movie/top_rated")
    fun getMovieTopRate(@Query("api_key") api_key:String,
                        @Query("language") language:String,
                        @Query("page") page:Int) : Observable<MovieTopRateResponse>

    @GET("3/movie/popular")
    fun getMoviePopular(@Query("api_key") api_key:String,
                        @Query("language") language:String,
                        @Query("page") page:Int) : Observable<MoviePopularResponse>

    @GET("3/movie/now_playing")
    fun getMovieNowPlaying(@Query("api_key") api_key:String,
                        @Query("language") language:String,
                        @Query("page") page:Int) : Observable<MovieNowPlayingResponse>

    @GET("/3/movie/{movie_id}")
    fun getDetailMovieById(@Path("movie_id") movie_id:Int,
                           @Query("api_key") api_key:String,
                           @Query("language") language:String) : Observable<DetailMovieResponse>

    @GET("/3/movie/{movie_id}/videos")
    fun getTrailerMovieById(@Path("movie_id") movie_id:Int,
                            @Query("api_key") api_key:String,
                            @Query("language") language:String) : Observable<TrailerResponse>

    @GET("/3/movie/{movie_id}/similar")
    fun getSimilarMovieById(@Path("movie_id") movie_id:Int,
                            @Query("api_key") api_key:String,
                            @Query("language") language:String,
                            @Query("page") page:Int) : Observable<SimilarResponse>

    @GET("/3/search/keyword")
    fun getNameByQuery(@Query("api_key") api_key:String,
                       @Query("query") query:String) : Observable<SearchResultResponse>

    @GET("/3/search/movie")
    fun getMovieByName(@Query("api_key") api_key:String,
                       @Query("query") query:String) : Observable<SearchMovieByNameResponse>

    companion object{
        var BASE_URL = "https://api.themoviedb.org"
        fun create() : APIService{
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
            return retrofit.create()
        }
    }
}