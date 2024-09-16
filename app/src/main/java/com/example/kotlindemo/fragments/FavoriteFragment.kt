package com.example.kotlindemo.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.Database.SharedPreferencesUtil
import com.example.kotlindemo.ImageViewerActivity
import com.example.kotlindemo.Model.MediaItem
import com.example.kotlindemo.R
import com.example.kotlindemo.VideoViewActivity
import com.example.kotlindemo.adapter.Fav_AudioAdapter
import com.example.kotlindemo.adapter.FavoriteAdapter
import java.util.Date


class FavoriteFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tv_nodata: TextView
    private lateinit var mediaAdapter: FavoriteAdapter
    private var favoriteMediaItems: MutableList<MediaItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recyclerView = view.findViewById(R.id.recyclerViewFavorites)
        tv_nodata = view.findViewById(R.id.tv_nodata)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)

        mediaAdapter = FavoriteAdapter(
            requireContext(),
            favoriteMediaItems,
            { item -> onItemClick(item) },
            { item -> onFavoriteClick(item) }
        )

        recyclerView.adapter = mediaAdapter
        updateUI()
        loadFavoriteItems()
    }

    private fun updateUI() {

        if (favoriteMediaItems.isEmpty()) {
            recyclerView.visibility = View.GONE
            tv_nodata.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            tv_nodata.visibility = View.GONE
        }
    }

    private fun loadFavoriteItems() {
        val favoriteIds = SharedPreferencesUtil.getAllFavoriteItemIds(requireContext())

        if (favoriteIds.isNotEmpty()) {
            favoriteMediaItems.clear() // Clear existing items
            favoriteMediaItems.addAll(getMediaItemsByIds(favoriteIds))
            mediaAdapter.updateData(favoriteMediaItems)
        } else {
            favoriteMediaItems.clear()
            mediaAdapter.updateData(favoriteMediaItems)
            Toast.makeText(requireContext(), "No favorite items found", Toast.LENGTH_SHORT).show()
        }
        updateUI ()
    }


    @SuppressLint("Range")
    private fun getMediaItemsByIds(ids: List<Int>): List<MediaItem> {
        val mediaItems = mutableListOf<MediaItem>()

        // Example: Fetch media items from a content provider or a database
        // This is a placeholder and should be replaced with your actual data retrieval logic.
        val projection = arrayOf(
            MediaStore.MediaColumns._ID,
            MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.DATE_ADDED,
            MediaStore.MediaColumns.MIME_TYPE,
            MediaStore.Images.Media.DISPLAY_NAME
        )

        val selection = "${MediaStore.MediaColumns._ID} IN (${ids.joinToString(",")})"
        val cursor: Cursor? = requireContext().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            "${MediaStore.MediaColumns.DATE_ADDED} DESC"
        )

        cursor?.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndex(MediaStore.MediaColumns._ID))
                val data = it.getString(it.getColumnIndex(MediaStore.MediaColumns.DATA))
                val dateAdded = it.getLong(it.getColumnIndex(MediaStore.MediaColumns.DATE_ADDED))
                val mimeType = it.getString(it.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE))
                val name =
                    it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))

                val item = MediaItem(
                    id,
                    data,
                    Date(dateAdded * 1000),
                    mimeType,
                    name,
                    MediaItem.TYPE_MEDIA,
                    MediaItem.Type.IMAGE
                )
                mediaItems.add(item)
            }
        }

        return mediaItems
    }


    private fun onItemClick(item: MediaItem) {
        // Handle item click, e.g., open the item in a detail view
        val intent = when {
            item.mimeType.startsWith("image/") -> {
                Intent(requireContext(), ImageViewerActivity::class.java).apply {
                    putStringArrayListExtra(
                        "IMAGE_URIS",
                        ArrayList(favoriteMediaItems.map { it.filePath })
                    )
                    putExtra("SELECTED_IMAGE", item.filePath)
                    putExtra("SELECTED_IMAGE_NAME", item.title)
                }
            }

            item.mimeType.startsWith("video/") -> {
                Intent(requireContext(), VideoViewActivity::class.java).apply {
                    putStringArrayListExtra(
                        "VIDEO_URIS",
                        ArrayList(favoriteMediaItems.map { it.filePath })
                    )
                    putExtra("SELECTED_VIDEO", item.filePath)
                    putExtra("SELECTED_VIDEO_NAME", item.title)
                }

            }


            else -> return
        }
        startActivity(intent)
    }


    private fun onFavoriteClick(item: MediaItem) {
        val isFavorite = SharedPreferencesUtil.isFavorite(requireContext(), item.id)
        if (isFavorite) {
            // Remove from favorites
            SharedPreferencesUtil.saveFavoriteStatus(requireContext(), item.id, false)
            favoriteMediaItems.remove(item)
            mediaAdapter.updateData(favoriteMediaItems)

            Toast.makeText(requireContext(), "Removed from favorites", Toast.LENGTH_SHORT).show()
        } else {
            // Handle the case if you want to add item to favorites again
            SharedPreferencesUtil.saveFavoriteStatus(requireContext(), item.id, true)
            favoriteMediaItems.add(item)
            mediaAdapter.updateData(favoriteMediaItems)
            Toast.makeText(requireContext(), "Added to favorites", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onResume() {
        super.onResume()
        loadFavoriteItems()
        updateUI()
    }

}