package com.example.greentea.fteam

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.greentea.fteam.record.VideoActivity
import kotlinx.android.synthetic.main.fragment_new_compe.*

class NewCompFragment : Fragment() {

//    lateinit var parent:MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_new_compe, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        parent = Activity() as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        camStartButton.setOnClickListener {
            val intent = Intent(this@NewCompFragment.context, VideoActivity::class.java)
            startActivity(intent)
        }
    }
}
