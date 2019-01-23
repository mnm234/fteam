package com.example.greentea.fteam.home.myPage

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.greentea.fteam.MainActivity
import com.example.greentea.fteam.R
import com.example.greentea.fteam.signIn.SignInStatus
import kotlinx.android.synthetic.main.fragment_mypage.*

class mypageFragment : Fragment() {
    private lateinit var parent: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mypage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myPage_username_textView.setText(SignInStatus.mUserName)
        mypageRecyclerView.layoutManager = LinearLayoutManager(context, OrientationHelper.VERTICAL, false)
        mypageRecyclerView.adapter = mypageRecyclerAdapter(context, parent)
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
//        if(!myPage_username_textView.isFocusable){
//
//        }


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parent = activity as MainActivity
    }

}
