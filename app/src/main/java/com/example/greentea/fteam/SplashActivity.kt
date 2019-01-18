package com.example.greentea.fteam

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window

class SplashActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // タイトルを非表示にします。
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        // splash.xmlをViewに指定します。
        setContentView(R.layout.splash)
        val hdl = Handler()
        // 500ms遅延させてsplashHandlerを実行します。
        hdl.postDelayed(splashHandler(), 500)
    }

    internal inner class splashHandler : Runnable {
        override fun run() {
            // スプラッシュ完了後に実行するActivityを指定します。
            val intent = Intent(application, MainActivity::class.java)
            startActivity(intent)
            // SplashActivityを終了させます。
            this@SplashActivity.finish()
        }
    }
}