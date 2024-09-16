package com.example.kotlindemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlindemo.Database.SharedPreferencesHelper
import com.example.kotlindemo.Database.SharedPreferencesUtil
import com.example.kotlindemo.Model.AudioFile
import com.example.kotlindemo.Model.MediaItem
import com.example.kotlindemo.R

class MusicAdapter(val context: Context, val musicItem: List<AudioFile>, private val onItemClick: (AudioFile) -> Unit) :
    RecyclerView.Adapter<MusicAdapter.MyViewHolder>() {
    private val audioFilesList = musicItem.toMutableList()


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val textView: TextView = view.findViewById(R.id.title)
        val iv_audio_img: ImageView = view.findViewById(R.id.albumArt)
        val artist: TextView = view.findViewById(R.id.artist)
        val duration: TextView = view.findViewById(R.id.duration)
        val size: TextView = view.findViewById(R.id.size)
        val favoriteButton: ImageView = view.findViewById(R.id.fav)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.audio_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int = musicItem.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val musicItems = musicItem[position]
        holder.textView.text = musicItems.title
        holder.artist.text = musicItems.artist
        holder.duration.text = formatDuration(musicItems.duration)
        holder.size.text = formatSize(musicItems.size)

        if (musicItems.uri != null) {
            Glide.with(holder.itemView.context)
                .load(musicItems.albumArtUri)
                .placeholder(R.drawable.iv_music_album) // Placeholder image
                .into(holder.iv_audio_img)
        } else {
            holder.iv_audio_img.setImageResource(R.drawable.iv_music_album) // Default placeholder
        }
        holder.favoriteButton.setImageResource(
            if (SharedPreferencesUtil.isFavorite(context, musicItems.id.toInt())) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
        )

        holder.favoriteButton.setOnClickListener {
            val newFavoriteStatus = !SharedPreferencesUtil.isFavorite(context, musicItems.id.toInt())
            SharedPreferencesUtil.saveFavoriteStatus(context, musicItems.id.toInt(), newFavoriteStatus)
            holder.favoriteButton.setImageResource(
                if (newFavoriteStatus) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
            )
            musicItems.isFavorite = newFavoriteStatus
            // Notify fragment or activity about the change
            (context as? FavoriteListener)?.onFavoriteChanged(musicItems)
        }

        holder.itemView.setOnClickListener {
           onItemClick(musicItems)
        }

    }
    interface FavoriteListener {
        fun onFavoriteChanged(item: AudioFile)
    }



    private fun formatDuration(duration: Long): String {
        val minutes = (duration / 1000 / 60).toInt()
        val seconds = (duration / 1000 % 60).toInt()
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun formatSize(size: Long): String {
        val kbSize = size / 1024
        val mbSize = kbSize / 1024
        return if (mbSize > 0) "$mbSize MB" else "$kbSize KB"
    }
}