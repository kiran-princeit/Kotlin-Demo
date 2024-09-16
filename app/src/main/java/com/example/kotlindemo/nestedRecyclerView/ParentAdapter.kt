package com.example.kotlindemo.nestedRecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.R

class ParentAdapter(val parentList: List<ParentDataClass>) :
    RecyclerView.Adapter<ParentAdapter.ViewHolder>() {


    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        val tv_title: TextView = itemview.findViewById(R.id.tv_titles)

        val rv_child: RecyclerView = itemview.findViewById(R.id.rv_child)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.nested_parent_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return parentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val aralist = parentList[position]

        holder.tv_title.text = aralist.title
        holder.rv_child.setHasFixedSize(true)
        holder.rv_child.layoutManager =
            GridLayoutManager(holder.itemView.context, 1, RecyclerView.HORIZONTAL, false)
        val adapter = ChildAdapter(aralist.childList)
        holder.rv_child.adapter = adapter
    }
}