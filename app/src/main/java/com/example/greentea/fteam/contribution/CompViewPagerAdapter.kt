//package com.example.greentea.fteam.contribution
//
//import android.support.v4.app.Fragment
//import android.support.v4.app.FragmentManager
//import android.support.v4.app.FragmentPagerAdapter
//
//
//class CompViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
//
//    private val tabTitles = arrayOf<CharSequence>("既存の競技", "競技を作成")
//
//    override fun getPageTitle(position: Int): CharSequence {
//        return tabTitles[position]
//    }
//
//    override fun getItem(position: Int): Fragment? {
//        return when (position) {
//            0 -> ExistingCompFragment()
//            1 -> NewCompFragment()
//            else -> null
//        }
//    }
//
//    override fun getCount(): Int {
//        return tabTitles.size
//    }
//}