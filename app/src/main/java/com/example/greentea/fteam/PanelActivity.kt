package com.example.greentea.fteam

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
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

    private fun requireSignIn(){
        AlertDialog.Builder(this).apply {
            setTitle("サインインしてね")
            setMessage("この機能はサインインしないと利用できません。")
            setPositiveButton("SignIn") { _, _ ->
                val intent = Intent(this@PanelActivity, SignInActivity::class.java)
                startActivity(intent)
            }
            setNegativeButton("Cancel", null)
            show()
        }
    }

    override fun onClick(v: View?) {
        v?.let {
            val intent = Intent(this, MainActivity::class.java)
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
                        requireSignIn()
                        return@let
                    }
                }
                R.id.myAccount_cardView -> {
                    if(SignInStatus.isSignIn){
                        intent.putExtra(MAIN_BOTTOM_NAV_KEY, MAIN_MY_PAGE_BOTTOM_NAV_ID)
                    } else {
                        requireSignIn()
                        return@let
                    }
                }
                R.id.camera_cardView -> {
                    if(SignInStatus.isSignIn){
                        intent.putExtra(MAIN_BOTTOM_NAV_KEY, MAIN_UPLOAD_BOTTOM_NAV_ID)
                    } else {
                        requireSignIn()
                        return@let
                    }
//                    intent.putExtra("Page", 1)
                }
                R.id.setting_cardView -> {
                    requireSignIn()
                    return@let
                }
            }
            startActivity(intent)
        }
    }
}
