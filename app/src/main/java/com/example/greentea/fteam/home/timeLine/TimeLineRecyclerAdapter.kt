package com.example.greentea.fteam.home.timeLine


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.greentea.fteam.MainActivity
import com.example.greentea.fteam.R
import com.example.greentea.fteam.`object`.TimeLineObject
import java.io.InputStream
import java.net.URL

class TimeLineRecyclerAdapter(val context: Context?, objects: MutableList<TimeLineObject> , val parent: MainActivity) : RecyclerView.Adapter<TimeLineRecyclerViewHolder>(), View.OnClickListener {

    private var mRecycler: RecyclerView? = null
    private var inflater: LayoutInflater? = null

    init {
        context?.run {
            inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
    }

    private val listItems = objects

//    var listItems: MutableList<TimeLineObject> = objects
//    var listID = mCompID

    override fun onClick(v: View) {
        val position = mRecycler!!.getChildAdapterPosition(v)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        mRecycler = null
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecycler = recyclerView
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TimeLineRecyclerViewHolder, position: Int) {
        holder.let{
            Log.d("unchi", "onBind")
            it.timelineIcon.setImageResource(R.drawable.ic_account_circle_black_24dp)
            it.timelineTime.text = listItems[position].timestamp.toString()
            it.timelineUsername.text = listItems[position].username
            /** ここでtypeによって表示する文章分岐 */
            val temp = when(listItems[position].type){
                "upload" -> {
                    "${listItems[position].username}さんが${listItems[position].compName}にチャレンジしました。"
                }
                else -> {
                    "error"
                }
            }
            it.timelineText.text = temp
//            val bitmap = BitmapFactory.decodeStream(URL("https://img.youtube.com/vi/${listItems[position].videoURL}/sddefault.jpg").content as InputStream?)
//            it.timelineThumbnail.foreground = BitmapDrawable(bitmap)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineRecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val mView = layoutInflater.inflate(R.layout.recycler_item_mypage, parent, false)
        mView.setOnClickListener { view ->
            mRecycler?.let {
            }
        }
        mView.setOnClickListener(this)
        return TimeLineRecyclerViewHolder(mView, this)
    }

}

class TimeLineRecyclerViewHolder(view: View, timelineRecyclerAdapter: TimeLineRecyclerAdapter) : RecyclerView.ViewHolder(view), View.OnClickListener {
    override fun onClick(v: View?) {
    }

//        独自に作成したListener
//    interface ItemClickListener {
//        fun onItemClick(view: View, position: Int) {
//        }
//    }

    val timelineIcon:ImageView = view.findViewById(R.id.timeline_icon)
    val timelineUsername:TextView = view.findViewById(R.id.timeline_username)
    val timelineTime:TextView = view.findViewById(R.id.timeline_time)
    val timelineText:TextView = view.findViewById(R.id.timeline_text)
    val timelineThumbnail: CardView = view.findViewById(R.id.timeline_cardView)

//    val compCardView: CardView = view.findViewById(R.id.compCardView)
//    val homeCompTextView: TextView = view.findViewById(R.id.homeCompTextView)
//    val compTime1: TextView = view.findViewById(R.id.homeCompTime1)
//    val compUserName1: TextView = view.findViewById(R.id.homeCompUserName1)
//    val compTime2: TextView = view.findViewById(R.id.homeCompTime2)
//    val compUserName2: TextView = view.findViewById(R.id.homeCompUserName2)
//    val compTime3: TextView = view.findViewById(R.id.homeCompTime3)
//    val compUserName3: TextView = view.findViewById(R.id.homeCompUserName3)
//    val top3Expand: ExpandableLayout = view.findViewById(R.id.top3Expand)



    init {
    }
}
