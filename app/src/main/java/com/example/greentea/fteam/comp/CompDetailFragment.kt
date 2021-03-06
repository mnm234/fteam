package com.example.greentea.fteam.comp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.greentea.fteam.*
import com.example.greentea.fteam.`object`.CompetitionDetailObject
import com.example.greentea.fteam.`object`.CompetitionObject
import com.example.greentea.fteam.contribution.record.VideoActivity
import com.example.greentea.fteam.signIn.SignInStatus
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_comp_detail.*

class CompDetailFragment : Fragment() {

    private lateinit var mFirebaseFirestore: FirebaseFirestore
    private lateinit var mCompID: String
    private lateinit var mCompName: String
    private val challengerList: MutableList<CompetitionDetailObject> = mutableListOf()
    private val idList: MutableList<String> = mutableListOf()
    private lateinit var parent: MainActivity
    private lateinit var mAdapter: CompDetailRecyclerViewAdapter
    private var isInit = false

    companion object {
        fun newInstance(mCompID: String, compName: String): CompDetailFragment {
            val compDetailFragment = CompDetailFragment()
            val bundle = Bundle()
            bundle.putString(COMP_ID_KEY, mCompID)
            bundle.putString(COMP_NAME_KEY, compName)
            compDetailFragment.arguments = bundle
            return compDetailFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mCompID = it.getString(COMP_ID_KEY)!!
            mCompName = it.getString(COMP_NAME_KEY)!!
        }
        mFirebaseFirestore = FirebaseFirestore.getInstance()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        parent = activity as MainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_comp_detail, container, false)
        view.setOnKeyListener { _, _, event ->
            if (event.keyCode == KeyEvent.KEYCODE_BACK) {
                if (event.action == KeyEvent.ACTION_UP) {
                    fragmentManager!!.popBackStack()
                    return@setOnKeyListener true
                } else {
                    return@setOnKeyListener false
                }
            }
            return@setOnKeyListener false
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        compDetailTitleTextView.text = mCompName

        mFirebaseFirestore.collection("competition")
                .document(mCompID)
                .get()
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) return@addOnCompleteListener
                    val data = task.result!!.toObject(CompetitionObject::class.java)
                    compDetailTimestampTextView.text = data?.timestamp.toString()
                    compDetailRuleTextView.text = data?.rule
                }

        mAdapter = CompDetailRecyclerViewAdapter(context, challengerList, idList, parent)
        compDetailRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
        }
        if (!isInit) {
            setupRecyclerView()
        } else {
            mAdapter.notifyDataSetChanged()
        }


        /** この競技に投稿 仮設置 */
        // ログインしていなければfalse
        comp_detail_challenge_button.isEnabled = SignInStatus.isSignIn
        comp_detail_challenge_button.setOnClickListener {
            val intent = Intent(this.context, VideoActivity::class.java)
            intent.putExtra(COMP_ID_KEY, mCompID)
            intent.putExtra(COMP_NAME_KEY, mCompName)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        mFirebaseFirestore.collection("competition")
                .document(mCompID)
                .collection("user")
                .orderBy("time")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (doc in task.result!!) {
                            idList.add(doc.id)
                            challengerList.add(doc.toObject(CompetitionDetailObject::class.java))
                        }
//                        compDetailRecyclerView.adapter = CompDetailRecyclerViewAdapter(context, challengerList, parent)
                        mAdapter.notifyDataSetChanged()
                        isInit = true
                    }
                }
    }
}
