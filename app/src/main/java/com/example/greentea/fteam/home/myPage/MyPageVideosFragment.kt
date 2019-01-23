package com.example.greentea.fteam.home.myPage

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.greentea.fteam.MainActivity
import com.example.greentea.fteam.R
import com.example.greentea.fteam.`object`.TimeLineObject
import com.example.greentea.fteam.signIn.SignInStatus
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_my_page_video.*

class MyPageVideosFragment : Fragment() {

    private lateinit var parent: MainActivity
    private lateinit var mFirebaseFirestore: FirebaseFirestore
    private val myVideosList: MutableList<TimeLineObject> = mutableListOf()
    private lateinit var myPageAdapter:myPageVideosRecyclerAdapter
    private var isInited = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_page_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mFirebaseFirestore = FirebaseFirestore.getInstance()

        myPageAdapter = myPageVideosRecyclerAdapter(context, myVideosList, parent)

        myPage_video_recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = myPageAdapter
        }
        if (!isInited) {
            setupRecyclerView()
        } else {
            myPageAdapter.notifyDataSetChanged()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parent = activity as MainActivity
    }

    private fun setupRecyclerView() {
        mFirebaseFirestore.collection("activity")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener { task ->
                    try {
                        if (task.isSuccessful) {
                            for (doc in task.result!!) {
                                // 取得した分をforEachで回す
                                val temp = doc.toObject(TimeLineObject::class.java)
                                if(temp.type == "upload" && temp.userID == SignInStatus.mUserID){
                                    myVideosList.add(temp)
                                }
                            }
                            myPageAdapter.notifyDataSetChanged()
                            isInited = true
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
    }
}
