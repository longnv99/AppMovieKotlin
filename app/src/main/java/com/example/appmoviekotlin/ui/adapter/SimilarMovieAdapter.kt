package com.example.appmoviekotlin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmoviekotlin.databinding.ItemMovieBinding
import com.example.appmoviekotlin.databinding.ItemSimilarMovieBinding
import com.example.appmoviekotlin.listener.MovieListener
import com.example.appmoviekotlin.response.model.Movie
import java.util.ArrayList

class SimilarMovieAdapter(val listener:MovieListener) : RecyclerView.Adapter<SimilarMovieAdapter.SimilarHolder>(){
    var list = ArrayList<Movie>()

    fun setDataListMovie(data : ArrayList<Movie>){
        var oldSize = list.size
        this.list.addAll(data)
        //notifyDataSetChanged()
        notifyItemRangeChanged(oldSize, list.size)
    }

    inner class SimilarHolder(val binding: ItemSimilarMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie : Movie){
            binding.similar = movie
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                listener.onMovieClick(it, movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSimilarMovieBinding.inflate(layoutInflater, parent, false)
        return SimilarHolder(binding)
    }

    override fun onBindViewHolder(holder: SimilarHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }


}