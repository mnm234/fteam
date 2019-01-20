package com.example.greentea.fteam

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.greentea.fteam.signIn.SignInActivity
import com.example.greentea.fteam.signIn.SignInStatus
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*

class PanelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // ログインしているかどうか判定
        val user = FirebaseAuth.getInstance().currentUser
        if(user != null){
            SignInStatus().isSignIn = true
            SignInStatus().mUser = user
        } else {
            SignInStatus().isSignIn = false
            SignInStatus().mUser = null
        }

        newcomp_sort_cardView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("BottomMenuId", 0)
            startActivity(intent)
        }

        hotcomp_sort_cardView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("BottomMenuId", 1)
            startActivity(intent)
        }

        myAccount_cardView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("BottomMenuId", 2)
            startActivity(intent)
        }

        camera_cardView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("BottomMenuId", 3)
            startActivity(intent)
        }

        setting_cardView.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

    }
}
