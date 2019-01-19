package com.example.greentea.fteam.contribution

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.greentea.fteam.R
import kotlinx.android.synthetic.main.fragment_upload.*


class UploadFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_upload, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CompViewPagerAdapter(childFragmentManager)
        compeViewPager.offscreenPageLimit = 2
        compeViewPager.adapter = adapter
        tabLayout.setupWithViewPager(compeViewPager)
    }
}
