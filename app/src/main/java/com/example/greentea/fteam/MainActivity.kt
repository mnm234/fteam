package com.example.greentea.fteam

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.example.greentea.fteam.Home.HomeFragment
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    companion object {

    }
    internal var toolbar: Toolbar? = null
    internal var searchtollbar: Toolbar? = null
    //        internal var bottom_navi:BottomNavigationView? = null
    var dialogBundle = Bundle()

    internal var search_menu: Menu? = null
    internal var item_search: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mStorageRef: StorageReference?=null
        mStorageRef = FirebaseStorage.getInstance().reference


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
}
