package com.example.greentea.fteam.contribution

import android.content.Context
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.greentea.fteam.COMP_ID_KEY
import com.example.greentea.fteam.MainActivity
import com.example.greentea.fteam.R
import com.example.greentea.fteam.contribution.record.VideoActivity

class ExistingCompRecyclerAdapter(val context: Context?, objects: MutableList<String>, val existCompIDList: MutableList<String>, val parent: MainActivity) : RecyclerView.Adapter<ExistingCompRecyclerViewHolder>(), View.OnClickListener{

    private var mRecycler: RecyclerView? = null
    private var inflater: LayoutInflater? = null
    private var listItems: MutableList<String>? = null

    init {
        context?.run {
            inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        listItems = objects
    }

//    interface ItemClickListener {
//        fun onItemClick(view: View, position: Int)
//    }

    /**
     * CardViewのClickListener
     */
    override fun onClick(v: View) {
        val position = mRecycler!!.getChildAdapterPosition(v)
        val intent = Intent(parent.baseContext, VideoActivity::class.java)
        intent.putExtra(COMP_ID_KEY, existCompIDList[position])
        v.context.startActivity(intent)
    }

    override fun onBindViewHolder(holder: ExistingCompRecyclerViewHolder, position: Int) {
        holder.let {
            it.existCompTitle.text = listItems!![position]
            it.existCompCardView.setOnClickListener(this)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecycler = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        mRecycler = null
    }

    override fun getItemCount(): Int {
        return listItems!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExistingCompRecyclerViewHolder {
        val mView = LayoutInflater.from(context).inflate(R.layout.recycler_item_exist_comp, parent, false)
        return ExistingCompRecyclerViewHolder(mView, this)
    }
}

class ExistingCompRecyclerViewHolder(view: View, ExistingCompRecyclerAdapter: ExistingCompRecyclerAdapter) : RecyclerView.ViewHolder(view){

    init {
    }
    val existCompTitle:TextView = view.findViewById(R.id.existCompTitleTextView)
    val existCompCardView:CardView = view.findViewById(R.id.existCompCardView)
}