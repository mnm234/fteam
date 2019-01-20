package com.example.greentea.fteam.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.greentea.fteam.R
import com.example.greentea.fteam.VIEW_PAGER_KEY
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private var page: Int = 0

    companion object {
        fun newInstance(page: Int): HomeFragment {
            val homeFragment = HomeFragment()
            val bundle = Bundle()
            bundle.putInt(VIEW_PAGER_KEY, page)
            homeFragment.arguments = bundle
            return homeFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            page = it.getInt(VIEW_PAGER_KEY, 0)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        tab1.text = "新しい競技"
//        tab2.text = "既存の競技"
        homeViewPager.offscreenPageLimit = 2
        homeViewPager.adapter = HomeViewPagerAdapter(childFragmentManager)
        // 開くページ指定
        homeViewPager.currentItem = page
        homeTabLayout.setupWithViewPager(homeViewPager)
    }
}
