package com.example.greentea.fteam.home

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.greentea.fteam.MainActivity
import com.example.greentea.fteam.R
import net.cachapa.expandablelayout.ExpandableLayout

class HomeRecyclerAdapter(val context: Context?, objects: MutableList<HomeRecyclerAdapter>, val parent: MainActivity) : RecyclerView.Adapter<HomeRecyclerViewHolder>(), View.OnClickListener {

    private var mRecycler: RecyclerView? = null
    private var inflater: LayoutInflater? = null

    init {
        context?.run {
            inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
    }

    var listItems: MutableList<HomeRecyclerAdapter> = objects

    override fun onClick(v: View) {
        val position = mRecycler!!.getChildAdapterPosition(v)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        mRecycler = null
    }

    override fun onBindViewHolder(holder: HomeRecyclerViewHolder, position: Int) {
        holder.let {
            it.homeCompTextView.text = "競技名"
            it.compTime1.text = "11:11:11"
            it.compUserName1.text = "ユーザー1"

            it.compTime2.text = "22:22:22"
            it.compUserName2.text = "ユーザー2"

            it.compTime3.text = "33:33:33"
            it.compUserName3.text = "ユーザー3"

            it.compCardView.setOnClickListener { _ ->
                it.top3Expand.isExpanded = !it.top3Expand.isExpanded
            }
        }
    }

    override fun getItemCount(): Int {
        /**
         * 何も取ってきていないので
         * ひとまず10件表示
         * */
        return 10
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecycler = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val mView = layoutInflater.inflate(R.layout.comp_item, parent, false)
        mView.setOnClickListener { view ->
            mRecycler?.let {
            }
        }
        mView.setOnClickListener(this)
        return HomeRecyclerViewHolder(mView, this)
    }

}

class HomeRecyclerViewHolder(view: View, HomeRecyclerAdapter: HomeRecyclerAdapter) : RecyclerView.ViewHolder(view), View.OnClickListener {
    override fun onClick(v: View?) {
    }

//        独自に作成したListener
//    interface ItemClickListener {
//        fun onItemClick(view: View, position: Int) {
//        }
//    }

    val compCardView:CardView = view.findViewById(R.id.compCardView)
    val homeCompTextView: TextView = view.findViewById(R.id.homeCompTextView)
    val compTime1: TextView = view.findViewById(R.id.homeCompTime1)
    val compUserName1:TextView = view.findViewById(R.id.homeCompUserName1)
    val compTime2: TextView = view.findViewById(R.id.homeCompTime2)
    val compUserName2:TextView = view.findViewById(R.id.homeCompUserName2)
    val compTime3: TextView = view.findViewById(R.id.homeCompTime3)
    val compUserName3:TextView = view.findViewById(R.id.homeCompUserName3)
    val top3Expand: ExpandableLayout = view.findViewById(R.id.top3Expand)

    init {

    }
}
