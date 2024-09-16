package com.example.kotlindemo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.ThumbnailUtils
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlindemo.Database.MediaDatabase
import com.example.kotlindemo.Database.MediaItemEntity
import com.example.kotlindemo.Database.SharedPreferencesUtil
import com.example.kotlindemo.ImageViewerActivity
import com.example.kotlindemo.Model.MediaItem
import com.example.kotlindemo.R
import com.example.kotlindemo.VideoViewActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

class MediaAdapter(private val context: Context, open var mediaItems: List<MediaItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val image = view.findViewById<ImageView>(R.id.iv_media)
        val media_name = view.findViewById<TextView>(R.id.tv_media_name)
        val favoriteButton: ImageView = view.findViewById(R.id.fav)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == MediaItem.TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.header_image, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.media_item, parent, false)
            MyViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = mediaItems[position]
        when (holder) {
            is HeaderViewHolder -> holder.headerText.text =
                item.filePath // filePath contains the date
            is MyViewHolder -> {
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
                holder.media_name.text = item.title
                holder.favoriteButton.setImageResource(
                    if (SharedPreferencesUtil.isFavorite(context, item.id)) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
                )

                holder.favoriteButton.setOnClickListener {
                    val newFavoriteStatus = !SharedPreferencesUtil.isFavorite(context, item.id)
                    SharedPreferencesUtil.saveFavoriteStatus(context, item.id, newFavoriteStatus)
                    holder.favoriteButton.setImageResource(
                        if (newFavoriteStatus) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
                    )
                    item.isFavorite = newFavoriteStatus
                    // Notify fragment or activity about the change
                    (context as? FavoriteListener)?.onFavoriteChanged(item)
                }
            }
        }

        holder.itemView.setOnClickListener(View.OnClickListener {

            if (item.mimeType.startsWith("image/")) {
                val imageUris =
                    mediaItems.map { it.filePath } // Assuming `data` is the URI or path to the image

                val intent = Intent(context, ImageViewerActivity::class.java).apply {
                    putStringArrayListExtra("IMAGE_URIS", ArrayList(imageUris))
                    putExtra("SELECTED_IMAGE", item.filePath)
                    putExtra("SELECTED_IMAGE_NAME", item.title)

                    Log.e("TAG", "onBindViewHolder: "+item.filePath )
                    Log.e("TAG", "title: "+item.title )
                    Log.e("TAG", "url: "+ArrayList(imageUris) )
                }
                context.startActivity(intent)

            }  else if (item.mimeType.startsWith("video/")) {
                val imageUris =
                    mediaItems.map { it.filePath } // Assuming `data` is the URI or path to the image

                val intent = Intent(context, VideoViewActivity::class.java).apply {
                    putStringArrayListExtra("VIDEO_URIS", ArrayList(imageUris))
                    putExtra("SELECTED_VIDEO", item.filePath)
                    putExtra("SELECTED_VIDEO_NAME", item.title)

                    Log.e("TAG", "VIDEO: "+item.filePath )
                    Log.e("TAG", "VIDEOtitle: "+item.title )
                    Log.e("TAG", "VIDEOurl: "+ArrayList(imageUris) )
                }
                context.startActivity(intent)
            }
        })

    }
    interface FavoriteListener {
        fun onFavoriteChanged(item: MediaItem)
    }

    override fun getItemCount(): Int {
        return mediaItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return mediaItems[position].itemType

    }
    fun updateData(newMediaItems: List<MediaItem>) {
        mediaItems = newMediaItems
        notifyDataSetChanged()
    }


    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headerText: TextView = itemView.findViewById(R.id.headerDate)
    }

}