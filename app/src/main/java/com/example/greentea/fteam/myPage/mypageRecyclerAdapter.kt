package com.example.greentea.fteam.myPage


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.greentea.fteam.MainActivity
import com.example.greentea.fteam.R
import com.google.firebase.firestore.FirebaseFirestore

class mypageRecyclerAdapter(val context: Context?, val parent: MainActivity) : RecyclerView.Adapter<mypageRecyclerViewHolder>(), View.OnClickListener {

    private var mRecycler: RecyclerView? = null
    private var inflater: LayoutInflater? = null
    private lateinit var mFirebaseFirestore: FirebaseFirestore

    init {
        context?.run {
            inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        mFirebaseFirestore = FirebaseFirestore.getInstance()
    }

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
//        return listItems.size
        return 5
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecycler = recyclerView
    }
    override fun onBindViewHolder(holder: mypageRecyclerViewHolder, position: Int) {
        holder.let{
            it.timelineIcon.setImageResource(R.drawable.ic_account_circle_black_24dp)
            it.timelineText.text = "○○さんが競技を作成しました。"
            it.timelineTime.text = "1 hour ago"
            it.timelineUsername.text = "Username"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mypageRecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val mView = layoutInflater.inflate(R.layout.recycler_item_mypage, parent, false)
        mView.setOnClickListener { view ->
            mRecycler?.let {
            }
        }
        mView.setOnClickListener(this)
        return mypageRecyclerViewHolder(mView, this)
    }

}

class mypageRecyclerViewHolder(view: View, mypageRecyclerAdapter: mypageRecyclerAdapter) : RecyclerView.ViewHolder(view), View.OnClickListener {
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
//    val timelineThumbnail:CardView = view.findViewById(R.id.timeline_cardView)

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
