package com.example.greentea.fteam.home.timeLine

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.greentea.fteam.R
import com.example.greentea.fteam.signIn.SignInStatus
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_time_line.*


class TimeLineFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_time_line, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timeline_sign_out_button.setOnClickListener {
            SignInStatus.signOut()
            timeline_sign_out_button.isEnabled = false
            timeline_sign_out_button.text = "サインインしろ"
        }
    }

    private fun timelineSetup(){
        val mFS = FirebaseFirestore.getInstance()
        // TL表示に使うObjectのArray宣言
        // TL初期化 Objectに格納していく
        for(follower in SignInStatus.followerList){
            mFS.collection("activity")
                    .whereEqualTo("userID", follower)
                    .orderBy("timestamp", Query.Direction.DESCENDING)
                    .addSnapshotListener { querySnapshot, firebaseFirestoreException ->  }
        }
        // timestamp順にならべadapterへ
    }
}
