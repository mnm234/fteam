package com.example.greentea.fteam.home

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.greentea.fteam.MainActivity
import com.example.greentea.fteam.R
import com.example.greentea.fteam.`object`.CompetitionObject
import com.example.greentea.fteam.`object`.CompetitionDetailObject
import com.google.firebase.firestore.FirebaseFirestore
import net.cachapa.expandablelayout.ExpandableLayout

class HomeRecyclerAdapter(val context: Context?, objects: MutableList<CompetitionObject>, mCompID:MutableList<String>, val parent: MainActivity) : RecyclerView.Adapter<HomeRecyclerViewHolder>(), View.OnClickListener {

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

    override fun onBindViewHolder(holder: HomeRecyclerViewHolder, position: Int) {
        holder.let {
            it.homeCompTextView.text = listItems[position].name
            mFirebaseFirestore.collection("competition")
                    .document(listID[position])
                    .collection("user")
                    .orderBy("time")
                    .limit(3)
                    .get()
                    .addOnCompleteListener {task ->
                        if(task.isSuccessful){
                            val tempObj = task.result!!.toObjects(CompetitionDetailObject::class.java)
                            var count = tempObj.size
                            if(count > 0){
                                it.compTime1.text = tempObj[0].time.toString()
                                it.compUserName1.text = tempObj[0].username
                                if(--count > 0) {
                                    it.compTime2.text = tempObj[1].time.toString()
                                    it.compUserName2.text = tempObj[1].username
                                    if(--count > 0){
                                        it.compTime3.text = tempObj[2].time.toString()
                                        it.compUserName3.text = tempObj[2].username
                                    }
                                }
                            }
                            it.compCardView.setOnClickListener { _ ->
                                Toast.makeText(this@HomeRecyclerAdapter.context, "クリック", Toast.LENGTH_SHORT)
                                        .show()
                                it.top3Expand.isExpanded = !it.top3Expand.isExpanded
                            }

                            it.top3Expand.setOnClickListener {
                                parent.goCompDetail(listID[position])
                            }
                        }
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

    val compCardView: CardView = view.findViewById(R.id.compCardView)
    val homeCompTextView: TextView = view.findViewById(R.id.homeCompTextView)
    val compTime1: TextView = view.findViewById(R.id.homeCompTime1)
    val compUserName1: TextView = view.findViewById(R.id.homeCompUserName1)
    val compTime2: TextView = view.findViewById(R.id.homeCompTime2)
    val compUserName2: TextView = view.findViewById(R.id.homeCompUserName2)
    val compTime3: TextView = view.findViewById(R.id.homeCompTime3)
    val compUserName3: TextView = view.findViewById(R.id.homeCompUserName3)
    val top3Expand: ExpandableLayout = view.findViewById(R.id.top3Expand)

    init {
    }
}
