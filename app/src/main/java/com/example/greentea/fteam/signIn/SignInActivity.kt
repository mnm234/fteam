package com.example.greentea.fteam.signIn

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.greentea.fteam.R


class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        supportFragmentManager!!.beginTransaction()
                .replace(R.id.sign_in_container, SignInFragment())
                .commit()
    }

    fun onFinishActivity(){
        this@SignInActivity.finish()
    }

//    fun goSignUp(){
//        supportFragmentManager!!.beginTransaction()
//                .replace(R.id.sign_in_container, SignUpFragment())
//                .commit()
//    }
}
