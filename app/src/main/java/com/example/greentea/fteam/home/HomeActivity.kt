package com.example.greentea.fteam.home

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.greentea.fteam.Login.LoginActivity
import com.example.greentea.fteam.MainActivity
import com.example.greentea.fteam.MyPage.mypageFragment
import com.example.greentea.fteam.NewFragment
import com.example.greentea.fteam.R
import com.example.greentea.fteam.contribution.UploadFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        newcomp_sort_cardView.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("BottomMenuId", 0)
            startActivity(intent)

//            supportFragmentManager!!.beginTransaction()
//                    .replace(R.id.container, NewFragment())
//                    .commit()

        }

        hotcomp_sort_cardView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("BottomMenuId", 1)
            startActivity(intent)
//            supportFragmentManager!!.beginTransaction()
//                    .replace(R.id.container, NewFragment())
//                    .commit()
        }

        myAccount_cardView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("BottomMenuId", 2)
            startActivity(intent)
//            supportFragmentManager!!.beginTransaction()
//                    .replace(R.id.container, mypageFragment())
//                    .commit()
        }

        camera_cardView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("BottomMenuId", 3)
            startActivity(intent)
//            supportFragmentManager!!.beginTransaction()
//                    .replace(R.id.container, UploadFragment())
//                    .commit()
        }

        setting_cardView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}
