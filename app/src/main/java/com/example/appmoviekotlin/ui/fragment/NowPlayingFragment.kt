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
import com.example.appmoviekotlin.BR
import com.example.appmoviekotlin.R
import com.example.appmoviekotlin.databinding.FragmentNowPlayingBinding
import com.example.appmoviekotlin.response.MovieNowPlayingResponse
import com.example.appmoviekotlin.response.MoviePopularResponse
import com.example.appmoviekotlin.ui.viewmodel.MainViewModel

class NowPlayingFragment : Fragment() {

    private lateinit var binding:FragmentNowPlayingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_now_playing, container, false)
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

    private fun makeAPICall() : MainViewModel {
        val viewModel = ViewModelProvider(requireActivity()).get<MainViewModel>()
        viewModel.makeApiCall(1)
        viewModel.getDataNowPlaying().observe(viewLifecycleOwner, Observer<MovieNowPlayingResponse> {
            if(it != null){
                viewModel.setDataNowPlaying(it.results)
            }
            else{
                Toast.makeText(activity, "error", Toast.LENGTH_LONG).show()
            }
        })
        return viewModel
    }

}