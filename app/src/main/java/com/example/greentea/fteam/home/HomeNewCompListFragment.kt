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
import com.example.greentea.fteam.`object`.CompetitionObject
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home_newcomplist.*
import java.lang.Exception


class HomeNewCompListFragment : Fragment() {
    private lateinit var parent: MainActivity
    private lateinit var mFirebaseFirestore: FirebaseFirestore
    private val compList: MutableList<CompetitionObject> = mutableListOf()
    private val compIDList: MutableList<String> = mutableListOf()

    companion object {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home_newcomplist, container, false)

        return view


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeRecyclerView.layoutManager = LinearLayoutManager(context, OrientationHelper.VERTICAL, false)
        mFirebaseFirestore = FirebaseFirestore.getInstance()
        onSetupRecyclerView()
//        homeRecyclerView.adapter = HomeRecyclerAdapter(context, mutableListOf(), parent)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        parent = activity as MainActivity
    }

    private fun onSetupRecyclerView() {
        mFirebaseFirestore.collection("competition")
                .get()
                .addOnCompleteListener { task ->
                    try {
                        if (task.isSuccessful) {
                            for (doc in task.result!!) {
                                // 取得した分をforEachで回す
                                compIDList.add(doc.id)
                                compList.add(doc.toObject(CompetitionObject::class.java))
                            }
                            homeRecyclerView.adapter = HomeRecyclerAdapter(context, compList, compIDList, parent)
                        }
                    } catch (e: Exception) {}
                }
    }
}
