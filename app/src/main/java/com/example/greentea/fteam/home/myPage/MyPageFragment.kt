package com.example.greentea.fteam.home.myPage

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.greentea.fteam.MainActivity
import com.example.greentea.fteam.R
import com.example.greentea.fteam.`object`.TimeLineObject
import com.example.greentea.fteam.`object`.UserObject
import com.example.greentea.fteam.signIn.SignInStatus
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_mypage.*

class MyPageFragment : Fragment() {
    private lateinit var parent: MainActivity
    private lateinit var mFirebaseFirestore: FirebaseFirestore

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mypage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mFirebaseFirestore = FirebaseFirestore.getInstance()

        myPage_username_textView.setText(SignInStatus.mUserName)

        myPageProfileEditButton.setOnClickListener {
            myPage_username_textView.isClickable = true
            myPage_username_textView.isFocusable = true
            myPage_username_textView.isFocusableInTouchMode = true
            myPage_username_textView.isEnabled = true
            myPage_username_textView.selectAll()
            myPage_username_textView.setBackgroundColor(Color.parseColor("#60ffffff"))
        }

        myPage_background.setOnClickListener {
            myPage_username_textView.isFocusable = false
            myPage_username_textView.setBackgroundColor(Color.parseColor("#00ffffff"))
            myPage_username_textView.isClickable = false
            myPage_username_textView.isEnabled = false
        }

        myPage_video.setOnClickListener {
            parent.goMyVideos()
        }
        myPage_follow.setOnClickListener {
            parent.goMyFollowers()
        }
        setupVideoCount()
        setupFollowerCount()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parent = activity as MainActivity
    }

    private fun setupVideoCount() {
        var count = 0
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
                                    count++
                                }
                            }
                            myPage_video_count.text = count.toString()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
    }
    private fun setupFollowerCount() {
        mFirebaseFirestore.collection("user")
                .document(SignInStatus.mUserID)
                .get()
                .addOnCompleteListener { task ->
                    try {
                        if (task.isSuccessful) {
                            myPage_follower_count.text = task.result?.toObject(UserObject::class.java)?.followerID?.size.toString()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
    }

}
