package com.example.appmoviekotlin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmoviekotlin.databinding.ItemSearchResultBinding
import com.example.appmoviekotlin.listener.NameMovieListener
import com.example.appmoviekotlin.response.model.Result
import java.util.ArrayList

class SearchAdapter(val listener: NameMovieListener) : RecyclerView.Adapter<SearchAdapter.SearchHolder>(){
    var list = ArrayList<Result>()

    fun setDataListMovie(data : ArrayList<Result>){
        this.list.clear()
        this.list = data
        notifyDataSetChanged()
    }
    inner class SearchHolder(val binding: ItemSearchResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(name : Result){
            binding.result = name
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                listener.onNameMovieClick(it, name)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchResultBinding.inflate(layoutInflater, parent, false)
        return SearchHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}