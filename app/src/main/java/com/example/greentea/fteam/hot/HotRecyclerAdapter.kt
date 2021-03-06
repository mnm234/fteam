package com.example.greentea.fteam.hot

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.greentea.fteam.MainActivity
import com.example.greentea.fteam.R
import com.example.greentea.fteam.`object`.CompetitionObject

class HotRecyclerAdapter(val context: Context?, objects: MutableList<CompetitionObject>, mCompID: MutableList<String>, val parent: MainActivity) : RecyclerView.Adapter<HotRecyclerViewHolder>(), View.OnClickListener {

    private var mRecycler: RecyclerView? = null
    private var inflater: LayoutInflater? = null

    init {
        context?.run {
            inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
    }

    private var listItems = objects
    private var listID = mCompID

    override fun onClick(v: View) {
//        val position = mRecycler!!.getChildAdapterPosition(v)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        mRecycler = null
    }

    override fun onBindViewHolder(holder: HotRecyclerViewHolder, position: Int) {
        holder.let {
            val name = listItems[position].name
            val timestamp = listItems[position].timestamp.toString()
            val rule = listItems[position].rule
            it.compListTitleTextView.text = name
            // timestamp
//            val sdf = SimpleDateFormat("MM/dd HH:mm:ss")
//            sdf.timeZone = TimeZone.getTimeZone("GMT")
//            val date = sdf.parse(listItems[position].timestamp.toString())
            it.compListTimestampTextView.text = timestamp
            // ルール
            it.compListRuleTextView.text = rule
            // cardViewクリックで詳細画面へ
            it.compListCardView.setOnClickListener {
                parent.goCompDetail(listID[position], name)
            }
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecycler = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotRecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val mView = layoutInflater.inflate(R.layout.recycler_item_comp, parent, false)
        mView.setOnClickListener {
            mRecycler?.let {
            }
        }
        mView.setOnClickListener(this)
        return HotRecyclerViewHolder(mView, this)
    }

}

class HotRecyclerViewHolder(view: View, HotRecyclerAdapter: HotRecyclerAdapter) : RecyclerView.ViewHolder(view), View.OnClickListener {
    override fun onClick(v: View?) {
    }

//        独自に作成したListener
//    interface ItemClickListener {
//        fun onItemClick(view: View, position: Int) {
//        }
//    }
    val compListCardView: CardView = view.findViewById(R.id.comp_list_cardView)
    val compListTitleTextView: TextView = view.findViewById(R.id.comp_list_title_textView)
    val compListTimestampTextView: TextView = view.findViewById(R.id.comp_list_timestamp_textView)
    val compListRuleTextView: TextView = view.findViewById(R.id.comp_list_rule_textView)
}
