package com.example.kotlindemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlindemo.Model.FolderItem
import com.example.kotlindemo.Model.MediaItem
import com.example.kotlindemo.R


class FolderAdapter(
    private val context: Context,
    var folderList: List<FolderItem>,
    private var onFolderClick: (FolderItem) -> Unit
) :
    RecyclerView.Adapter<FolderAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val thumbnailImageView: ImageView = itemView.findViewById(R.id.thumbnailImageView)

        private val folderNameTextView: TextView = itemView.findViewById(R.id.folderNameTextView)
        fun bind(folderItem: FolderItem) {

            folderNameTextView.text = folderItem.name
            if (folderItem.mediaItems.isNotEmpty()) {

                Glide.with(context)
                    .load(folderItem.mediaItems.first().filePath)
                    .into(thumbnailImageView)
            }

            itemView.setOnClickListener {
                onFolderClick(folderItem)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.folder_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return folderList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val folderItem = folderList[position]
        holder.bind(folderItem)
    }

    fun updateFolders(newFolders: List<FolderItem>) {
        folderList = newFolders
        notifyDataSetChanged()
    }
}