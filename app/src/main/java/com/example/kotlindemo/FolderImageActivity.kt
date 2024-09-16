package com.example.kotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.Model.MediaItem
import com.example.kotlindemo.adapter.MediaAdapter
import java.util.Date

class FolderImageActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mediaAdapter: MediaAdapter
    private var mediaItems: List<MediaItem> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folder_image)

        recyclerView = findViewById(R.id.rv_folder_iv)

        val folderName = intent.getStringExtra("folder_name")
        if (folderName != null) {
            loadMedia(folderName)
        }

        recyclerView.layoutManager = GridLayoutManager(this, 3)


    }


    private fun loadMedia(folderName: String) {
        val mediaItems = mutableListOf<MediaItem>()

        // Query for images
        val imageProjection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATA,
            MediaStore.MediaColumns.DATE_ADDED,
            MediaStore.MediaColumns.MIME_TYPE
        )
        val imageCursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            imageProjection,
            "${MediaStore.Images.Media.DATA} LIKE ?",
            arrayOf("$folderName/%"),
            null
        )

        imageCursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val dataColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            val dateAdd = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
            val mimeType = it.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)

            while (it.moveToNext()) {
                val id = it.getInt(idColumn)
                val data = it.getString(dataColumn)
                val mime_type = it.getString(mimeType)
                val date = it.getLong(dateAdd)

                mediaItems.add(
                    MediaItem(
                        id,
                        data,
                        Date(date * 1000),
                        mime_type,
                        "",
                        MediaItem.TYPE_MEDIA,
                        MediaItem.Type.IMAGE
                    )
                )
            }
        }

        // Query for videos
        val videoProjection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DATA,
            MediaStore.MediaColumns.DATE_ADDED,
            MediaStore.MediaColumns.MIME_TYPE
        )
        val videoCursor = contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            videoProjection,
            "${MediaStore.Video.Media.DATA} LIKE ?",
            arrayOf("$folderName/%"),
            null
        )

        videoCursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val dataColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            val dateAdd = it.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)
            val mimeType = it.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE)

            while (it.moveToNext()) {
                val id = it.getInt(idColumn)
                val data = it.getString(dataColumn)
                val mime_type = it.getString(mimeType)
                val date = it.getLong(dateAdd)

                mediaItems.add(
                    MediaItem(
                        id,
                        data,
                        Date(date * 1000),
                        mime_type,
                        "",
                        MediaItem.TYPE_MEDIA,
                        MediaItem.Type.VIDEO
                    )
                )
            }
        }

        mediaAdapter = MediaAdapter(this, mediaItems)
        recyclerView.adapter = mediaAdapter

    }
}