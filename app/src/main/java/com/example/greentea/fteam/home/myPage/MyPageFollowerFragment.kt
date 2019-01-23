package com.example.greentea.fteam.home.myPage

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
import com.example.greentea.fteam.`object`.UserObject
import com.example.greentea.fteam.signIn.SignInStatus
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_my_page_follower.*

class MyPageFollowerFragment : Fragment() {

    private lateinit var parent: MainActivity
    private lateinit var mFirebaseFirestore: FirebaseFirestore
    private var followerList: ArrayList<String> = ArrayList()
    private lateinit var myPageAdapter:MyPageFollowerRecyclerAdapter
    private var isInited = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_page_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mFirebaseFirestore = FirebaseFirestore.getInstance()

        myPageAdapter = MyPageFollowerRecyclerAdapter(context, followerList, parent)

        myPage_follower_recyclerView.apply {
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
        mFirebaseFirestore.collection("user")
                .document(SignInStatus.mUserID)
                .get()
                .addOnCompleteListener { task ->
                    try {
                        if (task.isSuccessful) {
                            followerList = task.result!!.toObject(UserObject::class.java)!!.followerID
                            myPage_follower_recyclerView.adapter = MyPageFollowerRecyclerAdapter(context, followerList, parent)
                            isInited = true
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
    }
}
