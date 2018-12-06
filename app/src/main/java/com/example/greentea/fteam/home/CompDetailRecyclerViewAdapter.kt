package com.example.greentea.fteam.home

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.VideoView
import com.example.greentea.fteam.MainActivity
import com.example.greentea.fteam.R
import com.example.greentea.fteam.`object`.CompetitionDetailObject

class CompDetailRecyclerViewAdapter(val context: Context?, objects: MutableList<CompetitionDetailObject>, val parent: MainActivity) : RecyclerView.Adapter<CompDetailRecyclerViewHolder>(), View.OnClickListener{

    private var mRecycler: RecyclerView? = null
    private var inflater: LayoutInflater? = null
    private var listItems: MutableList<CompetitionDetailObject>? = null

    init {
        context?.run {
            inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        listItems = objects
    }

//    interface ItemClickListener {
//        fun onItemClick(view: View, position: Int)
//    }
    override fun onClick(v: View) {
    }

    override fun onBindViewHolder(holder: CompDetailRecyclerViewHolder, position: Int) {
        holder.let {
            it.compDetailVideoView.setOnClickListener { _ ->
                if(it.compDetailVideoView.isPlaying){
                    // 再生中であればPause
                    it.compDetailVideoView.pause()
                } else {
                    it.compDetailVideoView.start()
                    if(!it.compDetailVideoView.isPlaying){
                        // 直前のstartで再生されていなければVideoを読み込んでいないと判断
                        it.compDetailVideoView.setVideoURI(Uri.parse(listItems!![position].videoURL))
                        it.compDetailVideoView.start()
                    }
                }
            }
            it.compDetailRankTextView.text = "${position + 1}位"
            it.compDetailTimeTextView.text = listItems!![position].time.toString()
            it.compDetailUserTextView.text = listItems!![position].username
        }
    }

    override fun getItemCount(): Int {
        return listItems!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompDetailRecyclerViewHolder {
        val mView = LayoutInflater.from(context).inflate(R.layout.comp_detail_item, parent, false)
        return CompDetailRecyclerViewHolder(mView, this)
    }
}

class CompDetailRecyclerViewHolder(view: View, CompDetailRecyclerViewAdapter: CompDetailRecyclerViewAdapter) : RecyclerView.ViewHolder(view){

    init {
    }
    val compDetailVideoView:VideoView = view.findViewById(R.id.compDetailVideoView)
    val compDetailRankTextView:TextView = view.findViewById(R.id.compDetailRankTextView)
    val compDetailTimeTextView:TextView = view.findViewById(R.id.compDetailTimeTextView)
    val compDetailUserTextView:TextView = view.findViewById(R.id.compDetailUserTextView)
}