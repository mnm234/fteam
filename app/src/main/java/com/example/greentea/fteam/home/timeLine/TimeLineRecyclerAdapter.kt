package com.example.greentea.fteam.home.timeLine


import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.greentea.fteam.MainActivity
import com.example.greentea.fteam.R
import com.example.greentea.fteam.`object`.TimeLineObject
import com.squareup.picasso.Picasso

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
//        val position = mRecycler!!.getChildAdapterPosition(v)
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
            it.timelineLayout.setOnClickListener {
                parent.goCompDetail(listItems[position].compID, listItems[position].compName)
            }
            it.timelineIcon.setImageResource(R.drawable.ic_account_circle_black_24dp)
            it.timelineTime.text = listItems[position].timestamp.toString()
            it.timelineUsername.text = listItems[position].username
            /** ここでtypeによって表示する文章と画像を分岐 */
            when(listItems[position].type){
                "upload" -> {
                    it.timelineText.text = "${listItems[position].username}さんが【${listItems[position].compName}】にチャレンジしました。"
                    Picasso.get()
                            .load("https://img.youtube.com/vi/${listItems[position].videoID}/sddefault.jpg")
                            .placeholder(R.drawable.gradientbackground)
                            .into(it.timelineThumbnailImageView)
                }
                "create_comp" -> {
                    it.timelineText.text = "${listItems[position].username}さんが新しい競技【${listItems[position].compName}】を作成しました。"
                    Picasso.get()
                            .load(R.drawable.document_rule_book)
                            .placeholder(R.drawable.gradientbackground)
                            .into(it.timelineThumbnailImageView)
                }
                else -> {
                    it.timelineText.text = "error"
                }
            }
            /**
             * Picasso 画像処理に特化したライブラリ
             * load 指定されたURLの画像を取得
             * placeholder 画像読み込み中に表示する画像
             * into 表示するImageViewを指定
             */
            /**
             * YoutubeのThumbnail取得URL一覧 画像サイズ昇順
             * ( 120 *   90) http://img.youtube.com/vi/動画ID/default.jpg
             * ( 320 *  180) http://img.youtube.com/vi/動画ID/mqdefault.jpg
             * ( 480 *  360) http://img.youtube.com/vi/動画ID/hqdefault.jpg
             * ( 640 *  480) http://img.youtube.com/vi/動画ID/sddefault.jpg
             * (1920 * 1080) http://img.youtube.com/vi/動画ID/maxresdefault.jpg
             */
//            Picasso.get()
//                    .load(imgUri)
//                    .placeholder(R.drawable.gradientbackground)
//                    .into(it.timelineThumbnailImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineRecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val mView = layoutInflater.inflate(R.layout.recycler_item_mypage, parent, false)
        mView.setOnClickListener {
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

    val timelineLayout:LinearLayout = view.findViewById(R.id.timeline_layout)
    val timelineIcon:ImageView = view.findViewById(R.id.timeline_icon)
    val timelineUsername:TextView = view.findViewById(R.id.timeline_username)
    val timelineTime:TextView = view.findViewById(R.id.timeline_time)
    val timelineText:TextView = view.findViewById(R.id.timeline_text)

    val timelineThumbnailImageView:ImageView = view.findViewById(R.id.timeline_thumbnail_image_view)
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
