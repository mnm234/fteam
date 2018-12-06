package com.example.greentea.fteam

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TabHost
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_upload.*


class UploadFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_upload, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val tab1 = tabLayout.newTab()
//        val tab2 = tabLayout.newTab()

        val adapter = CompeViewPagerAdapter(childFragmentManager)
//        tab1.text = "新しい競技"
//        tab2.text = "既存の競技"
        compeViewPager.offscreenPageLimit = 2
        compeViewPager.adapter = adapter
        tabLayout.setupWithViewPager(compeViewPager)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

    }

    companion object {
//        var parent:MainActivity? = null
    }
}
