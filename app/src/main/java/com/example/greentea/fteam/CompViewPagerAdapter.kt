package com.example.greentea.fteam

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter


class CompViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val tabTitles = arrayOf<CharSequence>("タブ1", "タブ2")

    override fun getPageTitle(position: Int): CharSequence {
        return tabTitles[position]
    }

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> NewCompFragment()
            1 -> ExistingCompFragment()
            else -> null
        }
    }

    override fun getCount(): Int {
        return tabTitles.size
    }
}