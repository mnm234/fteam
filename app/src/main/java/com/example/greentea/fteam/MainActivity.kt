package com.example.greentea.fteam

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.widget.ImageView
import com.example.greentea.fteam.comp.CompDetailFragment
import com.example.greentea.fteam.contribution.UploadFragment
import com.example.greentea.fteam.home.HomeFragment
import com.example.greentea.fteam.hot.HotFragment
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
        var page = 0
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val bottomNaviId = intent.getIntExtra(MAIN_BOTTOM_NAV_KEY, 0)
        val menubar = findViewById<ImageView>(R.id.menu)

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
                page = 1
            }
            MAIN_UPLOAD_BOTTOM_NAV_ID -> {
                navigation_bottom.selectedItemId = R.id.navigation_upload
                supportFragmentManager!!.beginTransaction()
                        .replace(R.id.container, UploadFragment())
                        .commit()
                page = 2
            }
        }

        menubar.setOnClickListener {
            openMenu()
        }

        navigation_bottom.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    if (SignInStatus.isSignIn) {
                        when {
                            page > 0 -> {
                                supportFragmentManager.beginTransaction()
                                        .setCustomAnimations(R.anim.anim_in_right, R.anim.anim_out_left)
                                        .replace(R.id.container, HomeFragment.newInstance(0))
                                        .commit()
                            }
                            else -> {
                                supportFragmentManager.beginTransaction()
                                        .replace(R.id.container, HomeFragment.newInstance(0))
                                        .commit()
                            }
                        }
                        page = 0
                        return@OnNavigationItemSelectedListener true
                    } else {
                        requireSignIn()
                        return@OnNavigationItemSelectedListener false
                    }
                }
                R.id.navigation_new -> {
                    when {
                        page < 1 -> {
                            supportFragmentManager.beginTransaction()
                                    .setCustomAnimations(R.anim.anim_in_left, R.anim.anim_out_right)
                                    .replace(R.id.container, NewArrivalsFragment())
                                    .commit()
                        }
                        page > 1 -> {
                            supportFragmentManager.beginTransaction()
                                    .setCustomAnimations(R.anim.anim_in_right, R.anim.anim_out_left)
                                    .replace(R.id.container, NewArrivalsFragment())
                                    .commit()
                        }
                        else -> {
                            supportFragmentManager.beginTransaction()
                                    .replace(R.id.container, NewArrivalsFragment())
                                    .commit()
                        }
                    }
                    page = 1
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_hot -> {
                    when {
                        page < 2 -> {
                            supportFragmentManager.beginTransaction()
                                    .setCustomAnimations(R.anim.anim_in_left, R.anim.anim_out_right)
                                    .replace(R.id.container, HotFragment())
                                    .commit()
                        }
                        page > 2 -> {
                            supportFragmentManager.beginTransaction()
                                    .setCustomAnimations(R.anim.anim_in_right, R.anim.anim_out_left)
                                    .replace(R.id.container, HotFragment())
                                    .commit()
                        }
                        else -> {
                            supportFragmentManager.beginTransaction()
                                    .replace(R.id.container, HotFragment())
                                    .commit()
                        }
                    }
                    page = 2
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_upload -> {
                    if (SignInStatus.isSignIn) {
                        when {
                            page < 3 -> {
                                supportFragmentManager.beginTransaction()
                                        .setCustomAnimations(R.anim.anim_in_left, R.anim.anim_out_right)
                                        .replace(R.id.container, UploadFragment())
                                        .commit()
                            }
                            else -> {
                                supportFragmentManager.beginTransaction()
                                        .replace(R.id.container, UploadFragment())
                                        .commit()
                            }
                        }
                        page = 3
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

    fun goCompDetail(mCompID: String, compName: String) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, CompDetailFragment.newInstance(mCompID, compName))
                .addToBackStack(null)
                .commit()
    }

    fun goOtherUser(mUid: String, mOName: String) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, OtherUserFragment.newInstance(mUid, mOName))
                .addToBackStack(null)
                .commit()
    }

    private fun openMenu() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
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
