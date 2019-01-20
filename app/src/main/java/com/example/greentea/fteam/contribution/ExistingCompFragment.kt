package com.example.greentea.fteam.contribution

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.greentea.fteam.MainActivity
import com.example.greentea.fteam.R
import com.example.greentea.fteam.`object`.CompetitionObject
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_existing_comp.*
import java.sql.Timestamp
import java.util.*

class ExistingCompFragment : Fragment() {

    private lateinit var mFirebaseFirestore: FirebaseFirestore
    private val existCompList: MutableList<String> = mutableListOf()
    private val existCompIDList: MutableList<String> = mutableListOf()
    private val existCompTimestampList:MutableList<Date> = mutableListOf()
    private lateinit var parent: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_existing_comp, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mFirebaseFirestore = FirebaseFirestore.getInstance()
        existCompRecyclerView.layoutManager = GridLayoutManager(context,1)
        onSetupExistCompList()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        parent = activity as MainActivity
    }

    /**
     * 競技リストのセットアップ
     */
    private fun onSetupExistCompList(){
        mFirebaseFirestore.collection("competition")
                .get()
                .addOnCompleteListener {task ->
                    if (task.isSuccessful) {
                        for (doc in task.result!!) {
                            // 取得した分をforEachで回す
                            existCompList.add(doc.toObject(CompetitionObject::class.java).name)
                            existCompIDList.add(doc.id)
                            existCompTimestampList.add(doc.toObject(CompetitionObject::class.java).timestamp!!)
                        }
                        existCompRecyclerView.adapter = ExistingCompRecyclerAdapter(context, existCompList, existCompIDList, parent)
                    }
                }
    }
}
