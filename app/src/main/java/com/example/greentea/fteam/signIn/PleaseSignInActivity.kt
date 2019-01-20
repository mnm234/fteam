package com.example.greentea.fteam.signIn

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.greentea.fteam.CREATE_ACCOUNT
import com.example.greentea.fteam.LOGIN
import com.example.greentea.fteam.LOGIN_MODE
import com.example.greentea.fteam.R
import kotlinx.android.synthetic.main.please_login_layout.*

class PleaseSignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_please_login)

        toLoginButton.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            intent.putExtra(LOGIN_MODE, LOGIN)
            startActivity(intent)
        }

        toCreateAccount.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            intent.putExtra(LOGIN_MODE, CREATE_ACCOUNT)
            startActivity(intent)
        }

    }
}
