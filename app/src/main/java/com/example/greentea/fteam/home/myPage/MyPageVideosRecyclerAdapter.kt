package com.example.greentea.fteam.home.myPage

import android.content.Context
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.greentea.fteam.*
import com.example.greentea.fteam.`object`.CompetitionObject
import com.example.greentea.fteam.`object`.TimeLineObject
import com.example.greentea.fteam.comp.YoutubeActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class myPageVideosRecyclerAdapter(val context: Context?, objects: MutableList<TimeLineObject>, val parent: MainActivity) : RecyclerView.Adapter<myPageVideosRecyclerViewHolder>(), View.OnClickListener {

    private var mRecycler: RecyclerView? = null
    private var inflater: LayoutInflater? = null

    init {
        context?.run {
            inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
    }

    private var listItems = objects

    override fun onClick(v: View) {
//        val position = mRecycler!!.getChildAdapterPosition(v)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        mRecycler = null
    }

    override fun onBindViewHolder(holder: myPageVideosRecyclerViewHolder, position: Int) {
        holder.let {
            Picasso.get()
                    .load("https://img.youtube.com/vi/${listItems[position].videoID}/sddefault.jpg")
                    .into(it.compDetailImageView)
            it.compDetailTimeTextView.text = convertTime(listItems[position].time)
            it.compDetailUserTextView.text = listItems[position].username
            it.compDetailCardView.setOnClickListener {
                parent.goCompDetail(listItems[position].compID, listItems[position].compName)
            }
            it.compDetailImageView.setOnClickListener {
                val intent = Intent(context, YoutubeActivity::class.java)
                intent.putExtra(YOUTUBE_ID_KEY, listItems[position].videoID)
                intent.putExtra(COMP_NAME_KEY, listItems[position].compName)
                intent.putExtra(USER_NAME_KEY, listItems[position].username)
                context!!.startActivity(intent)
            }
        }
    }

    /**
     * 時間(ms)を表示用に変換する関数
     * @param duration 変換対象の値(ms)
     * @return 00:00:00の表記でstring型で返す
     */
    private fun convertTime(duration: Int): String {
        val minute = (duration / (1000 * 60)) % 60
        val second = (duration / 1000) % 60
        val ms = duration % 1000
        return String.format("%02d:%02d:%03d", minute, second, ms)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecycler = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myPageVideosRecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val mView = layoutInflater.inflate(R.layout.recycler_item_comp_detail, parent, false)
        mView.setOnClickListener {
            mRecycler?.let {
            }
        }
        mView.setOnClickListener(this)
        return myPageVideosRecyclerViewHolder(mView, this)
    }
}

class myPageVideosRecyclerViewHolder(view: View, myPageVideosRecyclerAdapter: myPageVideosRecyclerAdapter) : RecyclerView.ViewHolder(view), View.OnClickListener {
    override fun onClick(v: View?) {
    }

//        独自に作成したListener
//    interface ItemClickListener {
//        fun onItemClick(view: View, position: Int) {
//        }
//    }

    val compDetailCardView:CardView = view.findViewById(R.id.comp_detail_card_view)
    val compDetailImageView: ImageView = view.findViewById(R.id.compDetailImageView)
    val compDetailTimeTextView:TextView = view.findViewById(R.id.compDetailTimeTextView)
    val compDetailUserTextView:TextView = view.findViewById(R.id.compDetailUserTextView)
}
