package com.example.greentea.fteam.comp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.greentea.fteam.COMP_ID_KEY
import com.example.greentea.fteam.MainActivity
import com.example.greentea.fteam.R
import com.example.greentea.fteam.`object`.CompetitionDetailObject
import com.example.greentea.fteam.`object`.CompetitionObject
import com.example.greentea.fteam.contribution.record.VideoActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_comp_detail.*

class CompDetailFragment : Fragment() {

    private lateinit var mFirebaseFirestore: FirebaseFirestore
    private lateinit var mCompID: String
    private val challengerList: MutableList<CompetitionDetailObject> = mutableListOf()
    private lateinit var parent: MainActivity

    companion object {
        fun newInstance(mCompID: String): CompDetailFragment {
            val compDetailFragment = CompDetailFragment()
            val bundle = Bundle()
            bundle.putString(COMP_ID_KEY, mCompID)
            compDetailFragment.arguments = bundle
            return compDetailFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        mCompID = args!!.getString(COMP_ID_KEY)!!
        mFirebaseFirestore = FirebaseFirestore.getInstance()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        parent = activity as MainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_comp_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compDetailRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        setupRecyclerView()

        /** この競技に投稿 仮設置 */
        comp_detail_challenge_button.setOnClickListener {
            val intent = Intent(this.context, VideoActivity::class.java)
            intent.putExtra(COMP_ID_KEY, mCompID)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        mFirebaseFirestore.collection("competition")
                .document(mCompID)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        compDetailTitleTextView.text = task.result!!.toObject(CompetitionObject::class.java)!!.name
                    }
                }
        mFirebaseFirestore.collection("competition")
                .document(mCompID)
                .collection("user")
                .orderBy("time")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (doc in task.result!!) {
                            challengerList.add(doc.toObject(CompetitionDetailObject::class.java))
                        }
                        compDetailRecyclerView.adapter = CompDetailRecyclerViewAdapter(context, challengerList, parent)
                    }
                }
    }
}
