package com.example.greentea.fteam.Home

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.greentea.fteam.MainActivity
import com.example.greentea.fteam.R
import kotlinx.android.synthetic.main.fragment_home.view.*
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
            //var rank = position+1
            //it.compeName.text = "$rank"+ "位"
            //it.compeTime.text = "00:00:00"
            //it.compeUserName.text = "ユーザ名"
            //var race = position+1
            it.textView.text = "競技名"
            it.compName1.text = "1位"
            it.compTime1.text = "11:11:11"

            it.compName2.text = "2位"
            it.compTime2.text = "22:22:22"

            it.compName3.text = "3位"
            it.compTime3.text = "33:33:33"
            it.textView.setOnClickListener {_ ->
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
//        Log.d("Share","getItemCount( ${listItems.size} )")

//        return listItems.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecycler = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val mView = layoutInflater.inflate(R.layout.race_item, parent, false)
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



//    val compeName:TextView = view.findViewById(R.id.compeName) as TextView
//    val compeTime:TextView = view.findViewById(R.id.compeTime) as TextView
//    val compeUserName:TextView = view.findViewById(R.id.compeUserName) as TextView

    val textView:TextView = view.findViewById(R.id.textView) as TextView
    val compName1: TextView = view.findViewById(R.id.compeName1) as TextView
    val compTime1:TextView = view.findViewById(R.id.compeTime1) as TextView
    val compName2: TextView = view.findViewById(R.id.compeName2) as TextView
    val compTime2:TextView = view.findViewById(R.id.compeTime2) as TextView
    val compName3: TextView = view.findViewById(R.id.compeName3) as TextView
    val compTime3:TextView = view.findViewById(R.id.compeTime3) as TextView
    val top3Expand = view.findViewById(R.id.top3Expand) as ExpandableLayout
    init {
    }
}
