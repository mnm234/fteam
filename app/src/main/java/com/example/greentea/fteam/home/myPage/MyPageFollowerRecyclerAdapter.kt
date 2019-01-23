package com.example.greentea.fteam.home.myPage

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.greentea.fteam.MainActivity
import com.example.greentea.fteam.R
import com.example.greentea.fteam.`object`.UserObject
import com.google.firebase.firestore.FirebaseFirestore

class MyPageFollowerRecyclerAdapter(val context: Context?, mFollowerID: MutableList<String>, val parent: MainActivity) : RecyclerView.Adapter<MyPageFollowerRecyclerViewHolder>(), View.OnClickListener {

    private var mRecycler: RecyclerView? = null
    private var inflater: LayoutInflater? = null
    private var mFS:FirebaseFirestore

    init {
        context?.run {
            inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        mFS = FirebaseFirestore.getInstance()
    }

    private var listItems = mFollowerID

    override fun onClick(v: View) {
//        val position = mRecycler!!.getChildAdapterPosition(v)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        mRecycler = null
    }

    override fun onBindViewHolder(holder: MyPageFollowerRecyclerViewHolder, position: Int) {
        holder.let {
            mFS.collection("user")
                    .document(listItems[position])
                    .get()
                    .addOnCompleteListener { task ->
                        if(!task.isSuccessful) return@addOnCompleteListener
                        val data = task.result?.toObject(UserObject::class.java)
                        it.followerUserName.text = data?.name
                        it.followerCardView.setOnClickListener {
                            parent.goOtherUser(task.result!!.id, data!!.name)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageFollowerRecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val mView = layoutInflater.inflate(R.layout.recycler_item_mypage_follower, parent, false)
        mView.setOnClickListener {
            mRecycler?.let {
            }
        }
        mView.setOnClickListener(this)
        return MyPageFollowerRecyclerViewHolder(mView, this)
    }

}

class MyPageFollowerRecyclerViewHolder(view: View, MyPageFollowerRecyclerAdapter: MyPageFollowerRecyclerAdapter) : RecyclerView.ViewHolder(view), View.OnClickListener {
    override fun onClick(v: View?) {
    }

//        独自に作成したListener
//    interface ItemClickListener {
//        fun onItemClick(view: View, position: Int) {
//        }
//    }

    val followerCardView: CardView = view.findViewById(R.id.follower_username_cardView)
    val followerUserName: TextView = view.findViewById(R.id.follower_username_textView)
}
