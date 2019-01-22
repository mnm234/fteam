package com.example.greentea.fteam.contribution.record

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.greentea.fteam.COMP_ID_KEY
import com.example.greentea.fteam.COMP_NAME_KEY
import com.example.greentea.fteam.R

class VideoActivity : AppCompatActivity() {

    private lateinit var mCompID:String
    private lateinit var mCompName:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        mCompID = intent.getStringExtra(COMP_ID_KEY)
        mCompName = intent.getStringExtra(COMP_NAME_KEY)

        savedInstanceState ?: supportFragmentManager.beginTransaction()
                .replace(R.id.container, VideoFragment.newInstance())
                .commit()
    }

    fun goCamera(){
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, VideoFragment.newInstance())
                .commit()
    }

    fun goUpload(path:String, name:String){
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, VideoUploadFragment.newInstance(path, name, mCompID, mCompName))
                .commit()
    }

}
