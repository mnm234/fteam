package com.example.greentea.fteam.home

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.greentea.fteam.MyPage.mypageFragment
import com.example.greentea.fteam.TimeLine.TimeLineFragment

class HomeViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val tabTitles = arrayOf<CharSequence>("TimeLine", "MyPage")

    override fun getPageTitle(position: Int): CharSequence {
        return tabTitles[position]
    }

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> TimeLineFragment()
            1 -> mypageFragment()
            else -> null
        }
    }

    override fun getCount(): Int {
        return tabTitles.size
    }
}