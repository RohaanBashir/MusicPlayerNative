package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerAdapter(Activity:AppCompatActivity) : FragmentStateAdapter(Activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                //  val homeFragment: HomeFragment = HomeFragment()
                MusicFragment()
            }
            1 -> {
                AboutFragment()
            }
            else -> favoriteFragment()
        }
    }
}