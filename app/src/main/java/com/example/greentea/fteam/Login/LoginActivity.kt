package com.example.greentea.fteam.Login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.greentea.fteam.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_new_comp.*

class LoginActivity : AppCompatActivity() {
    private var modeId:Int = 0 //0:Login 1:SignUp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        forgotPass.setOnClickListener {
            //forgot Passwordをクリックしたとき
        }

        typeChange.setOnClickListener {
            if(modeId == 0){
                forgotPass.visibility = View.GONE
                modeTitle.setImageResource(R.mipmap.signup)
                modeId = 1
                submitButton.text = "SIGN UP"
                typeChange.text = "I already have an account."

            }else{
                forgotPass.visibility = View.VISIBLE
                modeTitle.setImageResource(R.mipmap.login)
                modeId = 0
                submitButton.text = "Log In"
                typeChange.text = "I don't have an account."
            }
        }
    }
}
