package com.example.greentea.fteam

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.greentea.fteam.signIn.PleaseSignInActivity
import com.example.greentea.fteam.signIn.SignInActivity
import com.example.greentea.fteam.signIn.SignInStatus
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_panel.*

class PanelActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panel)

        // ログインしているかどうか判定
        val user = FirebaseAuth.getInstance().currentUser
        SignInStatus.isSignIn(user)

        newcomp_sort_cardView.setOnClickListener(this)
        hotcomp_sort_cardView.setOnClickListener(this)
        timeline_cardView.setOnClickListener(this)
        myAccount_cardView.setOnClickListener(this)
        camera_cardView.setOnClickListener(this)
        setting_cardView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        v?.let {
            var intent = Intent(this, MainActivity::class.java)
            when (it.id) {
                R.id.newcomp_sort_cardView -> {
                    intent.putExtra(MAIN_BOTTOM_NAV_KEY, MAIN_NEW_BOTTOM_NAV_ID)
                }
                R.id.hotcomp_sort_cardView -> {
                    // HOTに変える
                    intent.putExtra(MAIN_BOTTOM_NAV_KEY, MAIN_NEW_BOTTOM_NAV_ID)
                }
                R.id.timeline_cardView -> {
                    if(SignInStatus.isSignIn){
                        intent.putExtra(MAIN_BOTTOM_NAV_KEY, MAIN_HOME_BOTTOM_NAV_ID)
                    } else {
                        intent = Intent(this, PleaseSignInActivity::class.java)
                    }
                }
                R.id.myAccount_cardView -> {
                    if(SignInStatus.isSignIn){
                        intent.putExtra(MAIN_BOTTOM_NAV_KEY, MAIN_MY_PAGE_BOTTOM_NAV_ID)
                    } else {
                        intent = Intent(this, PleaseSignInActivity::class.java)
                    }
                }
                R.id.camera_cardView -> {
                    if(SignInStatus.isSignIn){
                        intent.putExtra(MAIN_BOTTOM_NAV_KEY, MAIN_UPLOAD_BOTTOM_NAV_ID)
                    } else {
                        intent = Intent(this, PleaseSignInActivity::class.java)
                    }
//                    intent.putExtra("Page", 1)
                }
                R.id.setting_cardView -> {
                    intent = Intent(this, SignInActivity::class.java)
                }
            }
            startActivity(intent)
        }
    }
}
