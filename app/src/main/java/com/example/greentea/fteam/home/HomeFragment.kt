package com.example.greentea.fteam.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.greentea.fteam.R
import kotlinx.android.synthetic.main.fragment_home.*
import android.R.attr.fragment
import com.example.greentea.fteam.MyPage.mypageFragment


class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newcomp_sort_cardView.setOnClickListener {
            fragmentManager!!.beginTransaction()
                    .replace(R.id.container, HomeNewCompListFragment.newInstance(0))
                    .commit()

        }

        hotcomp_sort_cardView.setOnClickListener {
            //HomeNewCompListFragment.newInstance(1)
            fragmentManager!!.beginTransaction()
                    .replace(R.id.container, HomeNewCompListFragment.newInstance(1))
                    .commit()
        }

        myAccount_cardView.setOnClickListener {
            fragmentManager!!.beginTransaction()
                    .replace(R.id.container, mypageFragment())
                    .commit()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    companion object {
    }
}
