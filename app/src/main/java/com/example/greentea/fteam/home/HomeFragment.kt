package com.example.greentea.fteam.home

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.greentea.fteam.MainActivity
import com.example.greentea.fteam.R
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {
    lateinit var parent: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeRecyclerView.layoutManager = LinearLayoutManager(context, OrientationHelper.VERTICAL, false)
        homeRecyclerView.adapter = HomeRecyclerAdapter(context, mutableListOf(), parent)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        parent = MainActivity()
    }
}
