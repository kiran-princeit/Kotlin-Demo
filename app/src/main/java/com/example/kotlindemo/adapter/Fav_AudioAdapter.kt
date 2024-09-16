package com.example.kotlindemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlindemo.Database.SharedPreferencesUtil
import com.example.kotlindemo.Model.AudioFile
import com.example.kotlindemo.R

class Fav_AudioAdapter(
    val context: Context,
    var favoriteitem: List<AudioFile>,
    private val onItemClick: (AudioFile) -> Unit,
    private val onFavoriteClick: (AudioFile) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class MyviewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.iv_media)
        val tv_media_name: TextView = view.findViewById(R.id.tv_media_name)
        val fav: ImageView = view.findViewById(R.id.fav)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.media_item, parent, false)
        return MyviewHolder(view)
    }


    override fun getItemCount(): Int = favoriteitem.size
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = favoriteitem[position]
        when (holder) {

            is MyviewHolder -> {
                holder.tv_media_name.text = item.title

                Glide.with(context).load(item.albumArtUri)
                    .placeholder(R.drawable.iv_music_album).into (holder.imageView)

                val isFavorite = SharedPreferencesUtil.isFavorite(context, item.id.toInt())
                holder.fav.setImageResource(
                    if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
                )

                holder.fav.setOnClickListener {
                    onFavoriteClick(item)
                }

                holder.itemView.setOnClickListener { onItemClick(item) }
            }
        }
    }

    fun updateData(newMediaItems: List<AudioFile>) {
        favoriteitem = newMediaItems
        notifyDataSetChanged()
    }


}


