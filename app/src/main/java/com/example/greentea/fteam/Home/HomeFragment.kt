package com.example.greentea.fteam.Home

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.greentea.fteam.MainActivity
import com.example.greentea.fteam.R
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {
    lateinit var parent: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private lateinit var adapter: HomeTop3RecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        top3Item.layoutManager = LinearLayoutManager(context, OrientationHelper.VERTICAL, false) as RecyclerView.LayoutManager?
        adapter = HomeTop3RecyclerAdapter(context, mutableListOf(), parent)
        top3Item.adapter = adapter
        text1.setOnClickListener {
            expandable_layout.isExpanded = !expandable_layout.isExpanded
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        parent = MainActivity()
    }



    companion object {

    }
}