package com.example.greentea.fteam

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.greentea.fteam.Login.LoginActivity
import com.example.greentea.fteam.Login.LoginInfo
import com.example.greentea.fteam.Login.PleaseLoginActivity
import com.example.greentea.fteam.MyPage.mypageFragment
import com.example.greentea.fteam.TimeLine.TimeLineFragment
import com.example.greentea.fteam.contribution.UploadFragment
import com.example.greentea.fteam.home.CompDetailFragment
import com.example.greentea.fteam.contribution.record.VideoActivity
import com.example.greentea.fteam.home.HomeFragment
import com.example.greentea.fteam.home.HomeNewCompListFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(){

//    internal var toolbar: Toolbar? = null
    internal var searchtollbar: Toolbar? = null
    var dialogBundle = Bundle()

    internal var search_menu: Menu? = null
    internal var item_search: MenuItem? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val bottomNaviId = intent.getIntExtra("BottomMenuId", 0)
        Toast.makeText(this, bottomNaviId.toString(), Toast.LENGTH_SHORT)

        when(bottomNaviId){
            0->{
                navigation_bottom.selectedItemId = R.id.navigation_home
                supportFragmentManager!!.beginTransaction()
                        .replace(R.id.container, HomeFragment())
                        .commit()
            }
            1->{
                navigation_bottom.selectedItemId = R.id.navigation_new
                supportFragmentManager!!.beginTransaction()
                        .replace(R.id.container, NewFragment())
                        .commit()
            }
            2->{
                if(LoginInfo().LoginFlag){
                    navigation_bottom.selectedItemId = R.id.navigation_home
                    supportFragmentManager!!.beginTransaction()
                            .replace(R.id.container, mypageFragment())
                            .commit()
                }else{
                    val intent = Intent(this, PleaseLoginActivity::class.java)
                    startActivity(intent)
                }

            }
            3->{
                navigation_bottom.selectedItemId = R.id.navigation_upload
                supportFragmentManager!!.beginTransaction()
                        .replace(R.id.container, UploadFragment())
                        .commit()
            }
        }

//        supportFragmentManager.beginTransaction()
//                .replace(R.id.container, TimeLineFragment())
//                .commit()

        navigation_bottom.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.container, TimeLineFragment())
                            .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_new -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.container, NewFragment())
                            .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_hot -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.container, HotFragment())
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
//        menubutton.setOnClickListener {
//            val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
//            drawer.openDrawer(GravityCompat.START)
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
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

    fun CameraIconPressed(){
        navigation_bottom.selectedItemId = R.id.navigation_upload
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, UploadFragment())
                .commit()
    }
}
