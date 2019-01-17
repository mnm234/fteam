package com.example.greentea.fteam.Login

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.greentea.fteam.R
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private var modeId:Int = 0 //0:Login 1:SignUp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        forgotPass.setOnClickListener {
            //forgot Passwordをクリックしたとき
        }

        typeChange.setOnClickListener {
            if(modeId == 0){
                forgotPass.visibility = View.GONE
                modeTitle.setImageResource(R.mipmap.signup)
                modeId = 1
                submitButton.text = "SIGN UP"
            }else{
                forgotPass.visibility = View.VISIBLE
                modeTitle.setImageResource(R.mipmap.login)
                modeId = 0
                submitButton.text = "Log In"
            }
        }


    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

}
