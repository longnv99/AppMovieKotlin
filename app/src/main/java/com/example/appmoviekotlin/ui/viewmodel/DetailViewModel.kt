package com.example.appmoviekotlin.ui.viewmodel

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appmoviekotlin.databinding.ActivityDetailBinding
import com.example.appmoviekotlin.listener.MovieListener
import com.example.appmoviekotlin.network.APIService
import com.example.appmoviekotlin.response.DetailMovieResponse
import com.example.appmoviekotlin.response.SimilarResponse
import com.example.appmoviekotlin.response.TrailerResponse
import com.example.appmoviekotlin.response.model.Movie
import com.example.appmoviekotlin.response.model.Trailer
import com.example.appmoviekotlin.ui.DetailActivity
import com.example.appmoviekotlin.ui.adapter.SimilarMovieAdapter
import com.example.appmoviekotlin.utils.Constants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.functions.Function3
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailViewModel : ViewModel(), MovieListener {
    var dispose : CompositeDisposable = CompositeDisposable()
    var detailRepository : MutableLiveData<DetailMovieResponse> = MutableLiveData()
    var trailerRepository : MutableLiveData<TrailerResponse> = MutableLiveData()
    var similarRepository : MutableLiveData<SimilarResponse> = MutableLiveData()
    var adapterSimilar : SimilarMovieAdapter = SimilarMovieAdapter(this)

    fun getDataDetailMovie() : MutableLiveData<DetailMovieResponse>{
        return detailRepository
    }
    fun getDataTrailer() : MutableLiveData<TrailerResponse>{
        return trailerRepository
    }
    fun getDataSimilarMovie() : MutableLiveData<SimilarResponse>{
        return similarRepository
    }
    fun getAdapterSimilarMovie() : SimilarMovieAdapter{
        return adapterSimilar
    }
    fun setDataSimilarMovie(data : ArrayList<Movie>){
        adapterSimilar.setDataListMovie(data)
    }
    fun makeApiCall(id:Int){
        val responseDetail = APIService.create().getDetailMovieById(id, Constants.API_KEY, Constants.LANGUAGE)
        val responseTrailer = APIService.create().getTrailerMovieById(id, Constants.API_KEY, Constants.LANGUAGE)
        val responseSimilar = APIService.create().getSimilarMovieById(id, Constants.API_KEY, Constants.LANGUAGE, 1)
        class ResponseModel (val detail:DetailMovieResponse, val trailer:TrailerResponse, val similar:SimilarResponse)
        dispose.add(
                Observable.zip(responseDetail, responseTrailer, responseSimilar,
                        Function3 { t1, t2, t3 -> ResponseModel(t1, t2, t3) })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(Consumer {
                            if (it != null) {
                                detailRepository.postValue(it.detail)
                                trailerRepository.postValue(it.trailer)
                                similarRepository.postValue(it.similar)
                            }
                        }, Consumer<Throwable> {
                            Log.e("failure", it.message.toString() )
                        })
        );
    }

    fun playTrailer(binding: ActivityDetailBinding, list:List<Trailer>){
        binding.youtube.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                for (t : Trailer in list){
                    youTubePlayer.loadVideo(t.key, 0f)
                }
            }
        });
    }

    override fun onCleared() {
        super.onCleared()
        dispose.dispose()
    }

    override fun onMovieClick(view: View, movie: Movie) {
        val intent = Intent(view.context, DetailActivity::class.java).apply { putExtra("movie_id", movie.id) }
        view.context.startActivity(intent)
    }
}