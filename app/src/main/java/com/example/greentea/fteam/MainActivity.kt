package com.example.greentea.fteam

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    companion object {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val homeFragment = HomeFragment()
        val playlistFragment = PlaylistFragment()
        val rankingFragment = RankingFragment()
        val historyFragment = HistoryFragment()
        val uploadFragment = UploadFragment()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, homeFragment)
        transaction.commit()


        navigation_bottom.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.container, homeFragment)
                    transaction.commit()

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_ranking -> {
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.container, rankingFragment)
                    transaction.commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_playlist -> {
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.container, playlistFragment)
                    transaction.commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_history -> {
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.container, historyFragment)
                    transaction.commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_upload -> {
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.container, uploadFragment)
                    transaction.commit()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })

    }

    fun toCamera(){
        val intent = Intent(this,CameraActivity::class.java)
        startActivity(intent)
    }
}
