package com.example.greentea.fteam.comp

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.greentea.fteam.R
import com.example.greentea.fteam.YOUTUBE_ID_KEY
import kotlinx.android.synthetic.main.activity_youtube.*

class YoutubeActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube)

        val videoID = intent.getStringExtra(YOUTUBE_ID_KEY)

        val data: String = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <style>\n" +
                "      div.player{font-size:0;}\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <!-- 1. The <iframe> (and video player) will replace this <div> tag. -->\n" +
                "    <style>* {margin:0; padding:0; height:100%; width:100%;}</style>\n" +
                "    <div class=\"player\">\n" +
                "    <div id=\"player\"></div>\n" +
                "    </div>\n" +
                "    <script src=\"file:///android_asset/youtube-player.js\">\n" +
                "    \n" +
                " \n" +
                "    </script>\n" +
                "    <script>\n" +
                "    var vId = \"" + videoID + "\";\n" +
                "    window.onload = function(){\n" +
                "\tloadVideoData();\n" +
                "      \n" +
                "    }\n" +
                "      //画面ロード時に動画IDの読み込み処理を行う\n" +
                "    function loadVideoData() {\n" +
                "\t\tloadPlayer(vId);\n" +
                "    }\n" +
                "    </script>\n" +
                "  </body>\n" +
                "</html>"
        youtubeVideoView?.settings?.javaScriptEnabled = true
        val settings = youtubeVideoView!!.settings
        settings.domStorageEnabled = true
        youtubeVideoView.loadDataWithBaseURL("https://youtube.com", data
                , "text/html", "UTF-8", null)
    }
}
