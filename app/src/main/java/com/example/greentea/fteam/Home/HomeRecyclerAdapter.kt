package com.example.greentea.fteam.Home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.greentea.fteam.MainActivity
import com.example.greentea.fteam.R

class HomeRecyclerAdapter(val context: Context?, objects: MutableList<HomeTop3RecyclerItem>, val parent: MainActivity) : RecyclerView.Adapter<HomeRecyclerViewHolder>(), View.OnClickListener {

    private var mRecycler: RecyclerView? = null
    private var inflater: LayoutInflater? = null

    init {
        context?.run {
            inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
    }

    var listItems: MutableList<HomeTop3RecyclerItem> = objects

    override fun onClick(v: View) {
        val position = mRecycler!!.getChildAdapterPosition(v)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        if (recyclerView != null) {
            super.onDetachedFromRecyclerView(recyclerView)
        }
        mRecycler = null
    }

    override fun onBindViewHolder(holder: HomeRecyclerViewHolder, position: Int) {
        holder?.let {
//            it.compeName.text = listItems[position].compeName
//            it.compeTime.text = listItems[position].compeTime
//            it.compeUserName.text = listItems[position].compeUserName
//            var rank = position+1
//            it.compeName.text = "$rank"+ "位"
//            it.compeTime.text = "00:00:00"
//            it.compeUserName.text = "ユーザ名"


        }
    }

    override fun getItemCount(): Int {
        /**
         * 何も取ってきていないので
         * ひとまず10件表示
         * */
        return 10
//        Log.d("Share","getItemCount( ${listItems.size} )")

//        return listItems.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecycler = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val mView = layoutInflater.inflate(R.layout.top3_recycler_item, parent, false)
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

    //    独自に作成したListener
//    interface ItemClickListener {
//        fun onItemClick(view: View, position: Int) {
//        }
//    }
    val adapter = HomeRecyclerAdapter

    
    
    val compeName:TextView = view.findViewById(R.id.compeName) as TextView
    val compeTime:TextView = view.findViewById(R.id.compeTime) as TextView
    val compeUserName:TextView = view.findViewById(R.id.compeUserName) as TextView

    init {
    }
}
