package com.example.appmoviekotlin.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appmoviekotlin.listener.MovieListener
import com.example.appmoviekotlin.network.APIService
import com.example.appmoviekotlin.response.MovieNowPlayingResponse
import com.example.appmoviekotlin.response.MoviePopularResponse
import com.example.appmoviekotlin.response.MovieUpComingResponse
import com.example.appmoviekotlin.response.MovieTopRateResponse
import com.example.appmoviekotlin.response.model.Movie
import com.example.appmoviekotlin.ui.DetailActivity
import com.example.appmoviekotlin.ui.MainActivity
import com.example.appmoviekotlin.ui.adapter.MovieAdapter
import com.example.appmoviekotlin.utils.Constants
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.functions.Function3
import io.reactivex.rxjava3.functions.Function4
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlin.collections.ArrayList

class MainViewModel : ViewModel(), MovieListener {
    var disposable : CompositeDisposable = CompositeDisposable()
    var upComingRepository : MutableLiveData<MovieUpComingResponse> = MutableLiveData()
    var topRateRepository : MutableLiveData<MovieTopRateResponse> = MutableLiveData()
    var popularRepository : MutableLiveData<MoviePopularResponse> = MutableLiveData()
    var nowPlayingRepository : MutableLiveData<MovieNowPlayingResponse> = MutableLiveData()
    var adapterUpComing: MovieAdapter = MovieAdapter(this)
    var adapterTopRate: MovieAdapter = MovieAdapter(this)
    var adapterPopular: MovieAdapter = MovieAdapter(this)
    var adapterNowPlaying: MovieAdapter = MovieAdapter(this)
    fun getUpComingAdpater():MovieAdapter{
        return adapterUpComing
    }
    fun getTopRateAdapter():MovieAdapter{
        return adapterTopRate
    }
    fun getPopularAdapter():MovieAdapter{
        return adapterPopular
    }
    fun getNowPlayingAdapter():MovieAdapter{
        return adapterNowPlaying
    }
    fun setDataUpComing(data:ArrayList<Movie>){
        adapterUpComing.setDataListMovie(data)
    }
    fun setDataTopRate(data: ArrayList<Movie>){
        adapterTopRate.setDataListMovie(data)
        adapterTopRate.notifyDataSetChanged()
    }
    fun setDataPopular(data: ArrayList<Movie>){
        adapterPopular.setDataListMovie(data)
        adapterPopular.notifyDataSetChanged()
    }
    fun setDataNowPlaying(data: ArrayList<Movie>){
        adapterNowPlaying.setDataListMovie(data)
        adapterNowPlaying.notifyDataSetChanged()
    }
    fun getDataUpComing():MutableLiveData<MovieUpComingResponse>{
        return upComingRepository
    }
    fun getDataTopRate():MutableLiveData<MovieTopRateResponse>{
        return topRateRepository
    }
    fun getDataPopular():MutableLiveData<MoviePopularResponse>{
        return popularRepository
    }
    fun getDataNowPlaying():MutableLiveData<MovieNowPlayingResponse>{
        return nowPlayingRepository
    }
    fun makeApiCall(page:Int){
//        val responseUpComing = APIService.create().getMovieUpComing(Constants.API_KEY,Constants.LANGUAGE,page)
        val responseTopRate = APIService.create().getMovieTopRate(Constants.API_KEY, Constants.LANGUAGE, page)
        val responsePopular= APIService.create().getMoviePopular(Constants.API_KEY, Constants.LANGUAGE, page)
        val responseNowPlaying = APIService.create().getMovieNowPlaying(Constants.API_KEY, Constants.LANGUAGE, page)
        class ResponseModel (val toprate:MovieTopRateResponse, val popular:MoviePopularResponse, val nowPlaying:MovieNowPlayingResponse)
        disposable.add(
            Observable.zip(responseTopRate, responsePopular, responseNowPlaying,
                Function3 { t1, t2, t3 -> ResponseModel(t1, t2, t3) })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                Consumer {
                    if(it != null){
//                        upComingRepository.postValue(it.upcoming)
                        topRateRepository.postValue(it.toprate)
                        popularRepository.postValue(it.popular)
                        nowPlayingRepository.postValue(it.nowPlaying)
                    }
                } , Consumer <Throwable>{
                    Log.e("failure", it.message.toString())
                }
            ));
    }
    fun makeApiCallUpComing(page:Int){
        val responseUpComing = APIService.create().getMovieUpComing(Constants.API_KEY,Constants.LANGUAGE,page)
        disposable.add(responseUpComing.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer {
                    if (it != null) {
                        upComingRepository.postValue(it)
                    }
                }, Consumer<Throwable> {
                    Log.e("failure", "makeApiCallUpComing error :"+it.message.toString())
                }));
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    override fun onMovieClick(view: View, movie: Movie) {
        val intent = Intent(view.context, DetailActivity::class.java).apply { putExtra("movie_id", movie.id) }
        view.context.startActivity(intent)
    }


}