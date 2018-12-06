package com.example.greentea.fteam

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.greentea.fteam.contribution.UploadFragment
import com.example.greentea.fteam.home.CompDetailFragment
import com.example.greentea.fteam.home.HomeFragment
import com.example.greentea.fteam.contribution.record.VideoActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(){

    internal var toolbar: Toolbar? = null
    internal var searchtollbar: Toolbar? = null
    //        internal var bottom_navi:BottomNavigationView? = null
    var dialogBundle = Bundle()

    internal var search_menu: Menu? = null
    internal var item_search: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment())
                .commit()

        navigation_bottom.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.container, HomeFragment())
                            .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_ranking -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.container, RankingFragment())
                            .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_playlist -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.container, PlaylistFragment())
                            .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_history -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.container, HistoryFragment())
                            .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_upload -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.container, UploadFragment())
                            .commit()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
        menubutton.setOnClickListener {
            val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
            drawer.openDrawer(GravityCompat.START)
        }
    }

    fun goCompDetail(mCompID:String){
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, CompDetailFragment.newInstance(mCompID))
                .commit()
    }

    fun goVideo(mCompID: String){
        Log.d("unchi","Mainだよ")
        val intent = Intent(this, VideoActivity::class.java)
        intent.putExtra(COMP_ID_KEY, mCompID)
        startActivity(intent)
    }
}
