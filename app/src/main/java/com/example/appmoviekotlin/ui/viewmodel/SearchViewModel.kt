package com.example.appmoviekotlin.ui.viewmodel

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appmoviekotlin.databinding.ActivitySearchBinding
import com.example.appmoviekotlin.listener.MovieListener
import com.example.appmoviekotlin.listener.NameMovieListener
import com.example.appmoviekotlin.network.APIService
import com.example.appmoviekotlin.response.SearchMovieByNameResponse
import com.example.appmoviekotlin.response.SearchResultResponse
import com.example.appmoviekotlin.response.model.Movie
import com.example.appmoviekotlin.response.model.Result
import com.example.appmoviekotlin.ui.DetailActivity
import com.example.appmoviekotlin.ui.adapter.MovieAdapter
import com.example.appmoviekotlin.ui.adapter.SearchAdapter
import com.example.appmoviekotlin.utils.Constants
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.functions.Predicate
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SearchViewModel : ViewModel() , NameMovieListener, MovieListener{
    var dispose : CompositeDisposable = CompositeDisposable()
    var nameMovieRepository : MutableLiveData<SearchResultResponse> = MutableLiveData()
    var movieRepository : MutableLiveData<SearchMovieByNameResponse> = MutableLiveData()
    var adapterName : SearchAdapter = SearchAdapter(this)
    var adapterMovie: MovieAdapter = MovieAdapter(this)

    fun getDataNameMovie() : MutableLiveData<SearchResultResponse>{
        return nameMovieRepository
    }
    fun getDataMovie() : MutableLiveData<SearchMovieByNameResponse>{
        return movieRepository
    }
    fun getAdapterNameMovie():SearchAdapter{
        return adapterName
    }
    fun getAdapterMovieSearch():MovieAdapter{
        return adapterMovie
    }
    fun setDataNameMovie(data : ArrayList<Result>){
        adapterName.setDataListMovie(data)
    }
    fun setDataMovieSearch(data: ArrayList<Movie>){
        adapterMovie.setDataListMovie(data)
    }

    fun makeApiCall(subject: Observable<String>){
        //call data name by query
        dispose.add(subject
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter(object : Predicate<String>{
                override fun test(t: String): Boolean {
                    if(t.isEmpty()){
                        return false
                    }
                    return true
                }
            })
            .distinctUntilChanged()
            .switchMap { t -> APIService.create().getNameByQuery(Constants.API_KEY, t.toString()) }
                .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer {
                if(it!=null){
                    nameMovieRepository.postValue(it)
                }
            }, Consumer<Throwable>{
                Log.e("failure", it.message.toString() )
            }));
    }

    override fun onNameMovieClick(view: View, name: Result) {
        //call data movie by name
        dispose.add(APIService.create().getMovieByName(Constants.API_KEY, name.name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer {
                if(it != null){
                    movieRepository.postValue(it)
                }
            },Consumer<Throwable>{
                Log.e("failure", it.message.toString() )
            }));
    }

    override fun onMovieClick(view: View, movie: Movie) {
        val intent = Intent(view.context, DetailActivity::class.java).apply { putExtra("movie_id", movie.id) }
        view.context.startActivity(intent)
    }

    override fun onCleared() {
        super.onCleared()
        dispose.dispose()
    }
}