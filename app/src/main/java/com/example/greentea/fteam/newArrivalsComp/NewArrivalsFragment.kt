package com.example.greentea.fteam.newArrivalsComp

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.greentea.fteam.MainActivity
import com.example.greentea.fteam.R
import com.example.greentea.fteam.`object`.CompetitionObject
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_new.*


class NewArrivalsFragment : Fragment() {

    private lateinit var parent: MainActivity
    private lateinit var mFirebaseFirestore: FirebaseFirestore
    private val compList: MutableList<CompetitionObject> = mutableListOf()
    private val compIDList: MutableList<String> = mutableListOf()
    private lateinit var newArrivalAdapter: NewArrivalsRecyclerAdapter
    private var isInited = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFirebaseFirestore = FirebaseFirestore.getInstance()


        newArrivalAdapter = NewArrivalsRecyclerAdapter(context, compList, compIDList, parent)
        newCompRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, OrientationHelper.VERTICAL, false)
            adapter = newArrivalAdapter
        }
        if (!isInited) {
            setupRecyclerView()
        } else {
            Log.d("unchi", compList.toString())
            newArrivalAdapter.notifyDataSetChanged()
        }

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        parent = activity as MainActivity
    }

    private fun setupRecyclerView() {
        mFirebaseFirestore.collection("competition")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener { task ->
                    try {
                        if (task.isSuccessful) {
                            for (doc in task.result!!) {
                                // 取得した分をforEachで回す
                                compIDList.add(doc.id)
                                compList.add(doc.toObject(CompetitionObject::class.java))
                            }
                            newArrivalAdapter.notifyDataSetChanged()
                            isInited = true
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
    }
}
