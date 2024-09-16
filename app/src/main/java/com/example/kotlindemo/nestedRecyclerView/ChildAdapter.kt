package com.example.kotlindemo.nestedRecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.R

class ChildAdapter(val childList: List<ChildDataClass>) :
    RecyclerView.Adapter<ChildAdapter.Vieholder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vieholder {
        return Vieholder(
            LayoutInflater.from(parent.context).inflate(R.layout.nested_child_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return childList.size
    }

    override fun onBindViewHolder(holder: Vieholder, position: Int) {

        val currentItem = childList[position]
        holder.childImage.setImageResource(currentItem.image)

    }

    class Vieholder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        val childImage: ImageView = itemview.findViewById(R.id.img_child)

    }
}