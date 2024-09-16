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
import com.example.kotlindemo.R
import com.example.kotlindemo.Movie

class VolleyDataAdapter(var context: Context, val volleyData: List<Movie>) :
    RecyclerView.Adapter<VolleyDataAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image_poster: ImageView = itemView.findViewById(R.id.image_poster)
        val tex_title: TextView = itemView.findViewById(R.id.tex_title)
        val tex_rating: TextView = itemView.findViewById(R.id.tex_rating)
        val text_des: TextView = itemView.findViewById(R.id.text_des)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.volley_data_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return volleyData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val arraylistdata = volleyData[position]

        holder.tex_title.text = arraylistdata.title
        holder.tex_rating.text = arraylistdata.rating.toString()
        holder.text_des.text = arraylistdata.overview
        Glide.with(context).load(arraylistdata.poster).load(holder.image_poster)


        Log.e("TAG", "onBindViewHolder: " + arraylistdata.poster)


    }
}