package com.example.kotlindemo.adapter

import android.content.Context
import android.media.ThumbnailUtils
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlindemo.Database.SharedPreferencesUtil
import com.example.kotlindemo.Model.MediaItem
import com.example.kotlindemo.R
import java.io.File

class FavoriteAdapter(
    val context: Context,
    var favoriteitem: List<MediaItem>,
    private val onItemClick: (MediaItem) -> Unit,
    private val onFavoriteClick: (MediaItem) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class MyviewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.iv_media)
        val mediaName: TextView = itemView.findViewById(R.id.tv_media_name)
        val favoriteIcon = itemView.findViewById<ImageView>(R.id.fav)
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headerText: TextView = itemView.findViewById(R.id.headerDate)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.header_image, parent, false)
                HeaderViewHolder(view)
            }

            else -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.media_item, parent, false)
                MyviewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = favoriteitem[position]
        when (holder) {
            is HeaderViewHolder -> holder.headerText.text = item.filePath
            is MyviewHolder -> {
                if (item.mimeType.startsWith("image/")) {
                    Glide.with(holder.itemView.context)
                        .load(File(item.filePath))
                        .into(holder.image)
                } else if (item.mimeType.startsWith("video/")) {
                    val thumbnail = ThumbnailUtils.createVideoThumbnail(
                        item.filePath,
                        MediaStore.Images.Thumbnails.MINI_KIND
                    )
                    holder.image.setImageBitmap(thumbnail)
                }
                holder.mediaName.text = item.title

                val isFavorite = SharedPreferencesUtil.isFavorite(context, item.id)
                holder.favoriteIcon.setImageResource(
                    if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
                )

                // Set click listener for favorite icon
                holder.favoriteIcon.setOnClickListener {
                    onFavoriteClick(item)
                }

                holder.itemView.setOnClickListener { onItemClick(item) }
            }
        }
    }

    fun updateData(newMediaItems: List<MediaItem>) {
        favoriteitem = newMediaItems
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = favoriteitem.size

    override fun getItemViewType(position: Int): Int {
        return favoriteitem[position].itemType
    }

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_MEDIA = 1
    }
}