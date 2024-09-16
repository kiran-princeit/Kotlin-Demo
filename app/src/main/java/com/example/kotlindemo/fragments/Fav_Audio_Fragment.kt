package com.example.kotlindemo.fragments

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Intent
import android.database.Cursor
import android.net.Uri
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
import com.example.kotlindemo.Model.AudioFile
import com.example.kotlindemo.Model.MediaItem
import com.example.kotlindemo.PlaybackActivity
import com.example.kotlindemo.R
import com.example.kotlindemo.VideoViewActivity
import com.example.kotlindemo.adapter.Fav_AudioAdapter
import com.example.kotlindemo.adapter.FavoriteAdapter
import java.util.Date


class Fav_Audio_Fragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tv_nodata: TextView
    private lateinit var mediaAdapter: Fav_AudioAdapter
    private var favoriteMediaItems: MutableList<AudioFile> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav__audio_, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerViewFavorites)
        tv_nodata = view.findViewById(R.id.tv_nodata)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)

        mediaAdapter = Fav_AudioAdapter(requireContext(),
            favoriteMediaItems,
            { item -> onItemClick(item) },
            { item -> onFavoriteClick(item) })

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
        updateUI()

    }


    @SuppressLint("Range")
    private fun getMediaItemsByIds(ids: List<Int>): List<AudioFile> {
        val mediaItems = mutableListOf<AudioFile>()

        // Example: Fetch media items from a content provider or a database
        // This is a placeholder and should be replaced with your actual data retrieval logic.
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM_ID
        )

        val selection = "${MediaStore.MediaColumns._ID} IN (${ids.joinToString(",")})"
        val cursor: Cursor? = requireContext().contentResolver.query(
//            MediaStore.Files.getContentUri("external"),
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            "${MediaStore.MediaColumns.DATE_ADDED} DESC"
        )


        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val durationColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val sizeColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)
            val artistColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val albumIdColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val title = it.getString(titleColumn)
                val duration = it.getLong(durationColumn)
                val size = it.getLong(sizeColumn)
                val artist = it.getString(artistColumn)
                val albumId = it.getLong(albumIdColumn)

                val uri =
                    ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)
                val albumArtUri = getAlbumArtUri(albumId)

                mediaItems.add(AudioFile(id, title, duration, size, artist, uri, albumArtUri, true))
            }
        }

        return mediaItems
    }

    private fun getAlbumArtUri(albumId: Long): Uri? {
        val albumUri =
            ContentUris.withAppendedId(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, albumId)
        val projection = arrayOf(MediaStore.Audio.Albums.ALBUM_ART)
        val cursor = requireContext().contentResolver.query(albumUri, projection, null, null, null)

        cursor?.use {
            if (it.moveToFirst()) {
                val artUriString =
                    it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ART))
                if (artUriString != null) {
                    return Uri.parse(artUriString)
                }
            }
        }
        return null
    }

    private fun onItemClick(audioFile: AudioFile) {
        val currentTrackIndex = favoriteMediaItems.indexOf(audioFile)
        val intent = Intent(requireContext(), PlaybackActivity::class.java).apply {
            putExtra("AUDIO_URI", audioFile.uri.toString())
            putExtra("TITLE", audioFile.title)
            putExtra("ARTIST", audioFile.artist)
            putParcelableArrayListExtra("AUDIO_FILES", ArrayList(favoriteMediaItems))
            putExtra("CURRENT_TRACK_INDEX", currentTrackIndex)
        }
        startActivity(intent)

    }


    private fun onFavoriteClick(item: AudioFile) {
        val isFavorite = SharedPreferencesUtil.isFavorite(requireContext(), item.id.toInt())
        if (isFavorite) {
            // Remove from favorites
            SharedPreferencesUtil.saveFavoriteStatus(requireContext(), item.id.toInt(), false)
            favoriteMediaItems.remove(item)
            mediaAdapter.updateData(favoriteMediaItems)

            Toast.makeText(requireContext(), "Removed from favorites", Toast.LENGTH_SHORT).show()
        } else {
            // Handle the case if you want to add item to favorites again
            SharedPreferencesUtil.saveFavoriteStatus(requireContext(), item.id.toInt(), true)
            favoriteMediaItems.add(item)
            mediaAdapter.updateData(favoriteMediaItems)
            Toast.makeText(requireContext(), "Added to favorites", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onResume() {
        super.onResume()
        // Refresh the favorite items list when returning to the activity
        loadFavoriteItems()
        updateUI()
    }

}