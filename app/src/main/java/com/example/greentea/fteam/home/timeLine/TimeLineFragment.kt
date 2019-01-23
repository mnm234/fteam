package com.example.greentea.fteam.home.timeLine

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.greentea.fteam.MainActivity
import com.example.greentea.fteam.R
import com.example.greentea.fteam.`object`.TimeLineObject
import com.example.greentea.fteam.signIn.SignInStatus
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.fragment_time_line.*


class TimeLineFragment : Fragment() {

    private lateinit var mFS:FirebaseFirestore
    private val timeLineList: MutableList<TimeLineObject> = mutableListOf()
    private lateinit var tlAdapter: TimeLineRecyclerAdapter
    private var registration:ListenerRegistration? = null
    private lateinit var parent:MainActivity
    private var isInited = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_time_line, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        parent = activity as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mFS = FirebaseFirestore.getInstance()

        tlAdapter = TimeLineRecyclerAdapter(context, timeLineList, parent)

        timeline_recyclerview.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = tlAdapter
        }

        if(!isInited){
            reloadTimeLine()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        /** 監視終了処理 */
        registration?.let {
            detachSnapshot()
        }
    }

    /** Activityを監視して追加があれば描画 */
    private fun reloadTimeLine(){
        registration = mFS.collection("activity")
                .orderBy("timestamp")
                .addSnapshotListener { querySnapshot, e ->
                    if(e != null){
                        e.printStackTrace()
                        return@addSnapshotListener
                    }
                    for(dc in querySnapshot!!.documentChanges){
                        when(dc.type){
                            DocumentChange.Type.ADDED -> {
                                Log.d("unchi","ADDED")
                                val tempData = dc.document.toObject(TimeLineObject::class.java)
                                if(SignInStatus.followerList.contains(tempData.userID)){
                                    timeLineList.add(0, tempData)
                                }
                            }
                            DocumentChange.Type.MODIFIED -> {}
                            DocumentChange.Type.REMOVED -> {}
                        }
                    }
//                    adapter = TimeLineRecyclerAdapter(context, timeLineList, parent)
//                    timeline_recyclerview.adapter = adapter
                    tlAdapter.notifyDataSetChanged()
                    isInited = true
                }
    }

    /** 監視終了 */
    private fun detachSnapshot(){
        registration?.remove()
    }
}
