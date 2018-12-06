package com.example.greentea.fteam

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


//        parent = Activity() as MainActivity


        val adapter = CompViewPagerAdapter(childFragmentManager)
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
