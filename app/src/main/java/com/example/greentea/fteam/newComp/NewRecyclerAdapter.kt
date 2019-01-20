package com.example.greentea.fteam.newComp

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.greentea.fteam.MainActivity
import com.example.greentea.fteam.R
import com.example.greentea.fteam.`object`.CompetitionObject
import com.google.firebase.firestore.FirebaseFirestore

class NewRecyclerAdapter(val context: Context?, objects: MutableList<CompetitionObject>, mCompID:MutableList<String>, val parent: MainActivity) : RecyclerView.Adapter<NewRecyclerViewHolder>(), View.OnClickListener {

    private var mRecycler: RecyclerView? = null
    private var inflater: LayoutInflater? = null
    private lateinit var mFirebaseFirestore: FirebaseFirestore

    init {
        context?.run {
            inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        mFirebaseFirestore = FirebaseFirestore.getInstance()
    }

    var listItems: MutableList<CompetitionObject> = objects
    var listID = mCompID

    override fun onClick(v: View) {
        val position = mRecycler!!.getChildAdapterPosition(v)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        mRecycler = null
    }

    override fun onBindViewHolder(holder: NewRecyclerViewHolder, position: Int) {
        holder.let {
            Log.d("unchi", "onBindViewHolder")
            it.compCardTextView.text = listItems[position].name
            it.compCardView.setOnClickListener {
                parent.goCompDetail(listID[position])
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewRecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val mView = layoutInflater.inflate(R.layout.recycler_item_new, parent, false)
        mView.setOnClickListener { view ->
            mRecycler?.let {
            }
        }
        mView.setOnClickListener(this)
        return NewRecyclerViewHolder(mView, this)
    }

}

class NewRecyclerViewHolder(view: View, NewRecyclerAdapter: NewRecyclerAdapter) : RecyclerView.ViewHolder(view), View.OnClickListener {
    override fun onClick(v: View?) {
    }

//        独自に作成したListener
//    interface ItemClickListener {
//        fun onItemClick(view: View, position: Int) {
//        }
//    }

    val compCardView:CardView = view.findViewById(R.id.new_comp_cardView)
    val compCardTextView:TextView = view.findViewById(R.id.new_comp_cardTextView)
}
