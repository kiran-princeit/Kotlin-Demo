package com.example.kotlindemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlindemo.Model.VolleyData
import com.example.kotlindemo.R
import com.squareup.picasso.Picasso

class AdapterDataVolley(val context: Context, val dataList: List<VolleyData>) :
    RecyclerView.Adapter<AdapterDataVolley.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.volley_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val listof = dataList[position]

        holder.id_field.text = listof.id.toString()
        holder.email_field.text = listof.email
        holder.first_name_field.text = listof.firstName
        holder.last_name_field.text = listof.lastName

        Picasso.get()
            .load(listof.avatar)
            .into(holder.id_avtar);
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val id_field: TextView = view.findViewById(R.id.id_field)
        val email_field: TextView = view.findViewById(R.id.email_field)
        val first_name_field: TextView = view.findViewById(R.id.first_name_field)
        val last_name_field: TextView = view.findViewById(R.id.last_name_field)
        val id_avtar: ImageView = view.findViewById(R.id.id_avtar)
    }
}