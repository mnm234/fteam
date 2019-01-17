package com.example.greentea.fteam.home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.support.annotation.NonNull
import android.view.View
import com.example.greentea.fteam.R


class HorizontalAdapter(private val context: Context, private val list: List<Any?>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        list[p1]?.let {
            (p0 as HorizontalAdapter.ContentViewHolder).setData(it, context)
        }
    }

//    override fun onBindViewHolder(p0: Nothing, p1: Int) {
//        (p0 as HorizontalAdapter.ContentViewHolder).setData(list[p1], context)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val defaultView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_horizontal, parent, false)
        return HorizontalAdapter.ContentViewHolder(defaultView)
    }


//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        (holder as HorizontalAdapter.ContentViewHolder).setData(list[position], context)
//    }

    override fun getItemCount(): Int {
        return list.size
    }

    internal class ContentViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        fun setData(item: Any, context: Context) {
            // 処理を実装
        }
    }
}