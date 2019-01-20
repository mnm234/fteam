package com.example.greentea.fteam.Login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.greentea.fteam.CREATE_ACCOUNT
import com.example.greentea.fteam.LOGIN
import com.example.greentea.fteam.R
import kotlinx.android.synthetic.main.please_login_layout.*

class PleaseLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_please_login)

        toLoginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("LoginMode", LOGIN)
            startActivity(intent)
        }

        toCreateAccount.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("LoginMode", CREATE_ACCOUNT)
            startActivity(intent)
        }

    }
}
