package com.example.greentea.fteam

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var parent:MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        dragView.setOnLongClickListener(this)
        // Viewをドラッグで動かす処理
        // LongClickはドラッグアンドドロップの処理
        // 動画投稿の際に使えるかも
        val listener = DragViewListener(dragsuruView)
        dragsuruView.setOnTouchListener(listener)

    }

//    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//        v!!.startDragAndDrop(null,View.DragShadowBuilder(v),v,0)
//        return true
//    }

//    override fun onLongClick(v: View?): Boolean {
//        v!!.startDragAndDrop(null,View.DragShadowBuilder(v),v,0)
//        return true
//    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }
    companion object {

    }
}
