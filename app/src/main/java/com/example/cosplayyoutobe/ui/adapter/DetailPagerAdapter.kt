package com.example.cosplayyoutobe.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cosplayyoutobe.ui.screen.add.AddFragment
import com.example.cosplayyoutobe.ui.screen.detail.VideoFragment
import com.example.cosplayyoutobe.ui.screen.home.HomeFragment

class DetailPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 1

    override fun createFragment(position: Int): Fragment {
        return AddFragment()
    }
}