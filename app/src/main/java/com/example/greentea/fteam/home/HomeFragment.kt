package com.example.greentea.fteam.home

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.greentea.fteam.MyPage.mypageFragment

import com.example.greentea.fteam.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private var page:Int = 1

    companion object {
        fun newInstance(viewpage:Int): HomeFragment {
            val bundle = Bundle()
            bundle.putInt("viewPage", viewpage)
            HomeFragment().arguments = bundle
            return HomeFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        page = arguments!!.getInt("viewPage", 1)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = HomeViewPagerAdapter(childFragmentManager)
//        tab1.text = "新しい競技"
//        tab2.text = "既存の競技"
        homeViewPager.offscreenPageLimit = 2
        homeViewPager.adapter = adapter
        if(page == 2){
            homeViewPager.currentItem = 2
        }
        homeViewPager.currentItem = 2
        homeTabLayout.setupWithViewPager(homeViewPager)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }
}
