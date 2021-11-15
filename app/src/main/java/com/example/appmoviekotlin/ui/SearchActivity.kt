package com.example.appmoviekotlin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.appmoviekotlin.BR
import com.example.appmoviekotlin.R
import com.example.appmoviekotlin.databinding.ActivitySearchBinding
import com.example.appmoviekotlin.ui.viewmodel.DetailViewModel
import com.example.appmoviekotlin.ui.viewmodel.SearchViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        listener()
        var viewModel = makeApiCall(getSubjectFromView(binding.edsearch))
        setupBinding(viewModel)
    }

    private fun setupBinding(viewModel: SearchViewModel){
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()
    }

    private fun makeApiCall(subject: Observable<String>) : SearchViewModel{
        val viewModel = ViewModelProvider(this).get<SearchViewModel>()
        viewModel.makeApiCall(subject)
        viewModel.getDataNameMovie().observe(this, Observer {
            if(it != null){
                viewModel.setDataNameMovie(it.results)
                binding.recycleview.adapter = viewModel.getAdapterNameMovie()
            }
        })
        viewModel.getDataMovie().observe(this, Observer {
            if(it != null){
                viewModel.setDataMovieSearch(it.results)
                binding.recycleview.adapter = viewModel.getAdapterMovieSearch()
            }
        })
        return viewModel
    }

    private fun listener(){
        binding.btnBack.setOnClickListener { this@SearchActivity.onBackPressed() }
    }

    private fun getSubjectFromView(editText : EditText) : Observable<String>{
        val subject: PublishSubject<String> = PublishSubject.create()
        editText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                subject.onNext(s.toString())
            }
        })
        return subject
    }
}