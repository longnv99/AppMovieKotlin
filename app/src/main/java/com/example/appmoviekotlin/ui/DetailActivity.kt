package com.example.appmoviekotlin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.appmoviekotlin.BR
import com.example.appmoviekotlin.R
import com.example.appmoviekotlin.databinding.ActivityDetailBinding
import com.example.appmoviekotlin.response.model.Movie
import com.example.appmoviekotlin.ui.viewmodel.DetailViewModel
import com.example.appmoviekotlin.ui.viewmodel.MainViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailBinding
    private var id : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        id = intent.getIntExtra("movie_id", 0)
        Toast.makeText(this, id.toString(), Toast.LENGTH_LONG).show()
        var viewModel = makeApiCall(id)
        setupBinding(viewModel)
    }

    private fun setupBinding(viewModel: DetailViewModel){
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()
    }

    private fun makeApiCall(movie_id : Int): DetailViewModel{
        val viewModel = ViewModelProvider(this).get<DetailViewModel>()
        viewModel.makeApiCall(movie_id)
        viewModel.getDataDetailMovie().observe(this, Observer {
            if(it != null){
                binding.detail = it
            }
            else binding.detail = null
        })
        viewModel.getDataTrailer().observe(this, Observer {
            if(it != null){
                viewModel.playTrailer(binding, it.results)
            }
        })
        viewModel.getDataSimilarMovie().observe(this, Observer {
            if (it != null) {
                viewModel.setDataSimilarMovie(it.results)
            }
        })
        return viewModel
    }
}