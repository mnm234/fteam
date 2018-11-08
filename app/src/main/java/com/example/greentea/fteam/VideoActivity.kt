package com.example.greentea.fteam

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class VideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        savedInstanceState ?: supportFragmentManager.beginTransaction()
                .replace(R.id.container, VideoFragment.newInstance())
                .commit()
    }

}
