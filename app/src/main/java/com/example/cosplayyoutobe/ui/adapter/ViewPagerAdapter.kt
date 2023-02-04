package com.example.cosplayyoutobe.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cosplayyoutobe.ui.screen.add.AddFragment
import com.example.cosplayyoutobe.ui.screen.home.HomeFragment
import com.example.cosplayyoutobe.ui.screen.lib.LibFragment
import com.example.cosplayyoutobe.ui.screen.search.SearchFragment
import com.example.cosplayyoutobe.ui.screen.channel.ChannelFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> HomeFragment()
        1 -> SearchFragment()
        2 -> LibFragment()
        else -> ChannelFragment()
    }
}