package com.example.greentea.fteam

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.example.greentea.fteam.comp.CompDetailFragment
import com.example.greentea.fteam.contribution.UploadFragment
import com.example.greentea.fteam.home.HomeFragment
import com.example.greentea.fteam.newArrivalsComp.NewArrivalsFragment
import com.example.greentea.fteam.signIn.SignInActivity
import com.example.greentea.fteam.signIn.SignInStatus
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.view.GravityCompat
import android.R
import android.media.Image
import android.support.v4.widget.DrawerLayout
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.google.android.youtube.player.internal.e


class MainActivity : AppCompatActivity() {

    //    internal var toolbar: Toolbar? = null
//    internal var searchtollbar: Toolbar? = null
//    var dialogBundle = Bundle()

//    internal var search_menu: Menu? = null
//    internal var item_search: MenuItem? = null

//    private var navi:View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.greentea.fteam.R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(com.example.greentea.fteam.R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val bottomNaviId = intent.getIntExtra(MAIN_BOTTOM_NAV_KEY, 0)
//        navi = findViewById<View>(R.id.navi_menu)
        val menubar = findViewById<ImageView>(com.example.greentea.fteam.R.id.menu)

        when (bottomNaviId) {
            MAIN_HOME_BOTTOM_NAV_ID -> {
                navigation_bottom.selectedItemId = com.example.greentea.fteam.R.id.navigation_home
                supportFragmentManager!!.beginTransaction()
                        .replace(com.example.greentea.fteam.R.id.container, HomeFragment.newInstance(0))
                        .commit()

            }
            MAIN_MY_PAGE_BOTTOM_NAV_ID -> {
                navigation_bottom.selectedItemId = com.example.greentea.fteam.R.id.navigation_home
                supportFragmentManager!!.beginTransaction()
                        .replace(com.example.greentea.fteam.R.id.container, HomeFragment.newInstance(1))
                        .commit()
            }
            MAIN_NEW_BOTTOM_NAV_ID -> {
                navigation_bottom.selectedItemId = com.example.greentea.fteam.R.id.navigation_new
                supportFragmentManager!!.beginTransaction()
                        .replace(com.example.greentea.fteam.R.id.container, NewArrivalsFragment())
                        .commit()
            }
            MAIN_UPLOAD_BOTTOM_NAV_ID -> {
                navigation_bottom.selectedItemId = com.example.greentea.fteam.R.id.navigation_upload
                supportFragmentManager!!.beginTransaction()
                        .replace(com.example.greentea.fteam.R.id.container, UploadFragment())
                        .commit()

            }
        }

        menubar.setOnClickListener {
            openMenu()
        }



        navigation_bottom.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                com.example.greentea.fteam.R.id.navigation_home -> {
                    if (SignInStatus.isSignIn) {
                        supportFragmentManager.beginTransaction()
                                .replace(com.example.greentea.fteam.R.id.container, HomeFragment.newInstance(0))
                                .commit()
                        return@OnNavigationItemSelectedListener true
                    } else {
                        requireSignIn()
                        return@OnNavigationItemSelectedListener false
                    }
                }
                com.example.greentea.fteam.R.id.navigation_new -> {
                    supportFragmentManager.beginTransaction()
                            .replace(com.example.greentea.fteam.R.id.container, NewArrivalsFragment())
                            .commit()
                    return@OnNavigationItemSelectedListener true
                }
                com.example.greentea.fteam.R.id.navigation_hot -> {
                    supportFragmentManager.beginTransaction()
                            .replace(com.example.greentea.fteam.R.id.container, HotFragment())
                            .commit()
                    return@OnNavigationItemSelectedListener true
                }
                com.example.greentea.fteam.R.id.navigation_upload -> {
                    if (SignInStatus.isSignIn) {
                        supportFragmentManager.beginTransaction()
                                .replace(com.example.greentea.fteam.R.id.container, UploadFragment())
                                .commit()
                        return@OnNavigationItemSelectedListener true
                    } else {
                        requireSignIn()
                        return@OnNavigationItemSelectedListener false
                    }
                }
            }
            return@OnNavigationItemSelectedListener false
        })
    }

    /** 未サインインでサインイン必要なページに行こうとした時 */
    private fun requireSignIn() {
        AlertDialog.Builder(this).apply {
            setTitle("サインインしてね")
            setMessage("この機能はサインインしないと利用できません。")
            setPositiveButton("SignIn") { _, _ ->
                val intent = Intent(this@MainActivity, SignInActivity::class.java)
                startActivity(intent)
            }
            setNegativeButton("Cancel", null)
            show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(com.example.greentea.fteam.R.menu.menu_home, menu)
        return true
    }

    fun goCompDetail(mCompID: String, compName:String) {
        supportFragmentManager.beginTransaction()
                .replace(com.example.greentea.fteam.R.id.container, CompDetailFragment.newInstance(mCompID, compName))
                .addToBackStack(null)
                .commit()
    }

    fun goOtherUser(mUid:String, mOName:String){
        supportFragmentManager.beginTransaction()
                .replace(com.example.greentea.fteam.R.id.container, OtherUserFragment.newInstance(mUid, mOName))
                .addToBackStack(null)
                .commit()
    }

    fun openMenu(){
        val drawer = findViewById<DrawerLayout>(com.example.greentea.fteam.R.id.drawer_layout)
        drawer.openDrawer(GravityCompat.START)
    }
//    fun goVideo(mCompID: String) {
//        val intent = Intent(this, VideoActivity::class.java)
//        intent.putExtra(COMP_ID_KEY, mCompID)
//        startActivity(intent)
//    }
//
//    fun CameraIconPressed() {
//        navigation_bottom.selectedItemId = R.id.navigation_upload
//        supportFragmentManager.beginTransaction()
//                .replace(R.id.container, UploadFragment())
//                .commit()
//    }
}
