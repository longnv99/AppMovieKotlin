package com.example.appmoviekotlin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmoviekotlin.databinding.ItemMovieBinding
import com.example.appmoviekotlin.listener.MovieListener
import com.example.appmoviekotlin.response.model.Movie
import java.util.*

class MovieAdapter(val listener:MovieListener) : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {
    var list = ArrayList<Movie>()

    fun setDataListMovie(data : ArrayList<Movie>){
        var oldSize = list.size
        this.list.addAll(data)
        //notifyDataSetChanged()
        notifyItemRangeChanged(oldSize, list.size)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)
        return MovieHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MovieHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie : Movie){
            binding.movieData = movie
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                listener.onMovieClick(it, movie)
            }
        }
    }
}