package com.example.kotlindemo.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.kotlindemo.Model.PaginationModel
import com.example.kotlindemo.R
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class PaginationAdapter(val context: Context, val dataList: List<PaginationModel>) :
    RecyclerView.Adapter<PaginationAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val iv_url: ImageView = view.findViewById(R.id.iv_url)
        val author: TextView = view.findViewById(R.id.tv_author)
        val tv_height: TextView = view.findViewById(R.id.tv_height)
        val tv_width: TextView = view.findViewById(R.id.tv_width)
        val tv_download: TextView = view.findViewById(R.id.tv_download)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.pagination_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = dataList[position]

        Glide.with(context).load(list.url).diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.iv_url)

        holder.author.text = list.author
        holder.tv_height.text = list.height.toString()
        holder.tv_width.text = list.width.toString()
        holder.tv_download.text = list.download

//        Picasso.get()
//            .load(list.url)
//            .into(holder.iv_url);


    }
}