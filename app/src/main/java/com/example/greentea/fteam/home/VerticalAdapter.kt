package com.example.greentea.fteam.home

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import com.example.greentea.fteam.R
import java.util.*


class VerticalAdapter(private val context: Context, private val list: List<ArrayList<Objects?>>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        list[p1]?.let { (p0 as ContentViewHolder).setData(it, context) }
    }

//    override fun onBindViewHolder(p0: Nothing, p1: Int) {
//        (p0 as ContentViewHolder).setData(list[p1], context)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val defaultView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_vertical, parent, false)
        return ContentViewHolder(defaultView)
    }

//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        (holder as ContentViewHolder).setData(list[position], context)
//    }

    override fun getItemCount(): Int {
        return list.size
    }

    internal class ContentViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        fun setData(item: Any, context: Context) {
            // Setting recycler view
            val recyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
            recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            // Generate demo data
            val demoList:ArrayList<Objects?> = ArrayList()
            demoList.add(null)
            demoList.add(null)
            demoList.add(null)
            demoList.add(null)
            demoList.add(null)
            // Setting adapter
            val adapter = HorizontalAdapter(context, demoList)
            recyclerView.swapAdapter(adapter, false)
        }
    }
}