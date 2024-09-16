package com.example.kotlindemo.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlindemo.Model.UserItem
import com.example.kotlindemo.R
import com.example.kotlindemo.ViewRetroDataActivity
import com.google.android.material.imageview.ShapeableImageView

class RetrofitAdapter(var context: Context, var list: List<UserItem>) :
    RecyclerView.Adapter<RetrofitAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        Glide.with(context).load(list.get(position).avatar_url).into(holder.iv_thumb)
        holder.tv_title.text = list[position].login
        holder.tv_data.text = list[position].type

        holder.itemView.setOnClickListener(View.OnClickListener {

            var intent = Intent(context, ViewRetroDataActivity::class.java)
            intent.putExtra("name", list[position].login)
            intent.putExtra("type", list[position].type)
            intent.putExtra("URL", list[position].avatar_url)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent)
        })
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var iv_thumb = itemView.findViewById<ShapeableImageView>(R.id.iv_thumb)
        var tv_title = itemView.findViewById<TextView>(R.id.tv_title)
        var tv_data = itemView.findViewById<TextView>(R.id.tv_data)
    }
}