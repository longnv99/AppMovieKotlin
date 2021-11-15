package com.example.appmoviekotlin.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.appmoviekotlin.ui.fragment.NowPlayingFragment
import com.example.appmoviekotlin.ui.fragment.PopularFragment
import com.example.appmoviekotlin.ui.fragment.TopRateFragment
import com.example.appmoviekotlin.ui.fragment.UpcomingFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 4;
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                UpcomingFragment()
            }
            1 -> {
                TopRateFragment()
            }
            2 -> {
                PopularFragment()
            }
            3 -> {
                NowPlayingFragment()
            }
            else -> {
                UpcomingFragment()
            }
        }
    }
}