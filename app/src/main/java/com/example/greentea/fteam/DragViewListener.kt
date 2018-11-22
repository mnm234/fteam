package com.example.greentea.fteam

import android.view.MotionEvent
import android.view.View
import android.widget.ImageView


// ドラッグしてViewを動かす処理を追加するリスナー
class DragViewListener(dv: View): View.OnTouchListener {

    private var dragView: View = dv
    private var oldx:Int = 0
    private var oldy:Int = 0

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        val x = event!!.rawX.toInt()
        val y = event.rawY.toInt()
        when(event.action){
            // 動いたとき
            MotionEvent.ACTION_MOVE -> {
                val left = dragView.left + (x - oldx)
                val top = dragView.top + (y - oldy)
                dragView.layout(left,top,left + dragView.width , top + dragView.height)
            }
        }
        // 今回の移動量の保持
        oldx = x
        oldy = y
        return true
    }




}