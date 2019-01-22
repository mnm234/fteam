package com.example.greentea.fteam.comp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import com.example.greentea.fteam.MainActivity
import com.example.greentea.fteam.R
import com.example.greentea.fteam.YOUTUBE_ID_KEY
import com.example.greentea.fteam.`object`.CompetitionDetailObject
import com.squareup.picasso.Picasso

class CompDetailRecyclerViewAdapter(val context: Context?, objects: MutableList<CompetitionDetailObject>, val parent: MainActivity) : RecyclerView.Adapter<CompDetailRecyclerViewHolder>(), View.OnClickListener{

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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CompDetailRecyclerViewHolder, position: Int) {
        holder.let {
            Picasso.get()
                    .load("https://img.youtube.com/vi/${listItems!![position].videoURL}/sddefault.jpg")
                    .into(it.compDetailImageView)
            it.compDetailImageView.setOnClickListener {
                val intent = Intent(context, YoutubeActivity::class.java)
                intent.putExtra(YOUTUBE_ID_KEY, listItems!![position].videoURL)
                context!!.startActivity(intent)
            }
            when(position){
                0 -> it.compDetailRankImageView.setImageResource(R.mipmap.no1)
                1 -> it.compDetailRankImageView.setImageResource(R.mipmap.no2)
                2 -> it.compDetailRankImageView.setImageResource(R.mipmap.no3)
                else -> {
                    it.compDetailRankTextView.visibility = View.VISIBLE
                    it.compDetailRankTextView.text = "${position + 1}位"
                }

            }
            it.compDetailTimeTextView.text = listItems!![position].time.toString()
            it.compDetailUserTextView.text = listItems!![position].username
            it.compDetailCardView.setOnClickListener {
                parent.goOtherUser(listItems!![position].userID, listItems!![position].username)
            }
        }
    }

    override fun getItemCount(): Int {
        return listItems!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompDetailRecyclerViewHolder {
        val mView = LayoutInflater.from(context).inflate(R.layout.recycler_item_comp_detail, parent, false)
        return CompDetailRecyclerViewHolder(mView, this)
    }
}

class CompDetailRecyclerViewHolder(view: View, CompDetailRecyclerViewAdapter: CompDetailRecyclerViewAdapter) : RecyclerView.ViewHolder(view){

    init {
    }
    val compDetailCardView:CardView = view.findViewById(R.id.comp_detail_card_view)
    val compDetailImageView:ImageView = view.findViewById(R.id.compDetailImageView)
    val compDetailRankTextView:TextView = view.findViewById(R.id.compDetailRankTextView)
    val compDetailRankImageView: ImageView = view.findViewById(R.id.compDetailRankImageView)
    val compDetailTimeTextView:TextView = view.findViewById(R.id.compDetailTimeTextView)
    val compDetailUserTextView:TextView = view.findViewById(R.id.compDetailUserTextView)
}