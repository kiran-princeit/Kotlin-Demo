package com.example.kotlindemo.Model

import android.graphics.Bitmap
import android.net.Uri
import java.util.Date

data class MediaItem(
    val id: Int,
    val filePath: String,
    val dateAdded: Date,
    val mimeType: String,
    val title: String,
    val itemType: Int,
    val type: Type,
    var isFavorite: Boolean = false
) {


    enum class Type {

        IMAGE, VIDEO, AUDIO
    }


    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_MEDIA = 1
    }

}
