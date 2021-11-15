package com.example.appmoviekotlin.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.RecyclerView
import com.example.appmoviekotlin.BR
import com.example.appmoviekotlin.R
import com.example.appmoviekotlin.databinding.FragmentUpcomingBinding
import com.example.appmoviekotlin.response.MovieUpComingResponse
import com.example.appmoviekotlin.ui.viewmodel.MainViewModel

class UpcomingFragment : Fragment() {

    private lateinit var binding: FragmentUpcomingBinding
    var currentPage : Int = 1
    var totalPage : Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_upcoming, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var viewModel = makeAPICall()
        setupBinding(viewModel)
    }

    private fun setupBinding(viewModel: MainViewModel){
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()
    }

    private fun makeAPICall() : MainViewModel{
        val viewModel = ViewModelProvider(requireActivity()).get<MainViewModel>()
        call(viewModel)
        binding.upcomingRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(binding.upcomingRecyclerView.canScrollVertically(1)){
                    if (currentPage <= totalPage){
                        currentPage+=1
                        call( viewModel)
                    }
                }
            }
        })
        return viewModel
    }
    fun call(viewModel: MainViewModel){
        viewModel.makeApiCallUpComing(currentPage)
        loading()
        viewModel.getDataUpComing().observe(viewLifecycleOwner, Observer<MovieUpComingResponse> {
            if (it != null) {
                loading()
                viewModel.setDataUpComing(it.results)
                totalPage = it.total_pages
            } else {
                Toast.makeText(activity, "error", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun loading(){
        if(currentPage == 1){
            binding.isLoading = binding.isLoading == null
        }else{
            binding.isLoadingMore = binding.isLoadingMore == null
        }
    }

}