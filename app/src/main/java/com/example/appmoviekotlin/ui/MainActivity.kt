package com.example.appmoviekotlin.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.appmoviekotlin.R
import com.example.appmoviekotlin.ui.adapter.ViewPagerAdapter
import com.example.appmoviekotlin.ui.viewmodel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navigationView = findViewById<BottomNavigationView>(R.id.bottomnavigation)
        val viewpager = findViewById<ViewPager2>(R.id.viewPager)

        viewpager.offscreenPageLimit = 3
        val adapter = ViewPagerAdapter(this)
        viewpager.adapter = adapter
        viewpager.registerOnPageChangeCallback(object : OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when(position){
                    0 ->{navigationView.menu.findItem(R.id.upcoming).setChecked(true)}
                    1 ->{navigationView.menu.findItem(R.id.toprate).setChecked(true)}
                    2 ->{navigationView.menu.findItem(R.id.popular).setChecked(true)}
                    3 ->{navigationView.menu.findItem(R.id.nowplaying).setChecked(true)}
                }
            }
        })
        navigationView.setOnItemSelectedListener(object : OnItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when(item.itemId){
                    R.id.upcoming -> {viewpager.currentItem = 0}
                    R.id.toprate -> {viewpager.currentItem = 1}
                    R.id.popular -> {viewpager.currentItem = 2}
                    R.id.nowplaying -> {viewpager.currentItem = 3}
                }
                return true
            }
        } )

    }

    fun clickSearch(view: View) {
        startActivity(Intent(this, SearchActivity::class.java))
    }
}