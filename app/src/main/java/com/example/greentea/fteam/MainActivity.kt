package com.example.greentea.fteam

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import com.example.greentea.fteam.comp.CompDetailFragment
import com.example.greentea.fteam.contribution.UploadFragment
import com.example.greentea.fteam.home.HomeFragment
import com.example.greentea.fteam.newArrivalsComp.NewArrivalsFragment
import com.example.greentea.fteam.signIn.SignInActivity
import com.example.greentea.fteam.signIn.SignInStatus
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    //    internal var toolbar: Toolbar? = null
//    internal var searchtollbar: Toolbar? = null
//    var dialogBundle = Bundle()

//    internal var search_menu: Menu? = null
//    internal var item_search: MenuItem? = null

//    private var navi:View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val bottomNaviId = intent.getIntExtra(MAIN_BOTTOM_NAV_KEY, 0)
//        navi = findViewById<View>(R.id.navi_menu)

        when (bottomNaviId) {
            MAIN_HOME_BOTTOM_NAV_ID -> {
                navigation_bottom.selectedItemId = R.id.navigation_home
                supportFragmentManager!!.beginTransaction()
                        .replace(R.id.container, HomeFragment.newInstance(0))
                        .commit()

            }
            MAIN_MY_PAGE_BOTTOM_NAV_ID -> {
                navigation_bottom.selectedItemId = R.id.navigation_home
                supportFragmentManager!!.beginTransaction()
                        .replace(R.id.container, HomeFragment.newInstance(1))
                        .commit()
            }
            MAIN_NEW_BOTTOM_NAV_ID -> {
                navigation_bottom.selectedItemId = R.id.navigation_new
                supportFragmentManager!!.beginTransaction()
                        .replace(R.id.container, NewArrivalsFragment())
                        .commit()
            }
            MAIN_UPLOAD_BOTTOM_NAV_ID -> {
                navigation_bottom.selectedItemId = R.id.navigation_upload
                supportFragmentManager!!.beginTransaction()
                        .replace(R.id.container, UploadFragment())
                        .commit()

            }
        }

        navigation_bottom.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    if (SignInStatus.isSignIn) {
                        supportFragmentManager.beginTransaction()
                                .replace(R.id.container, HomeFragment.newInstance(0))
                                .commit()
                        return@OnNavigationItemSelectedListener true
                    } else {
                        requireSignIn()
                        return@OnNavigationItemSelectedListener false
                    }
                }
                R.id.navigation_new -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.container, NewArrivalsFragment())
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
                    if (SignInStatus.isSignIn) {
                        supportFragmentManager.beginTransaction()
                                .replace(R.id.container, UploadFragment())
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
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    fun goCompDetail(mCompID: String, compName:String) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, CompDetailFragment.newInstance(mCompID, compName))
                .commit()
    }

    fun goOtherUser(mUid:String, mOName:String){
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, OtherUserFragment.newInstance(mUid, mOName))
                .commit()
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
