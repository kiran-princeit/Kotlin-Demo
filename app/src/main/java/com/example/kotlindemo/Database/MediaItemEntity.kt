package com.example.kotlindemo.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "media_items")
data class MediaItemEntity(

    @PrimaryKey val id: Int,
    val filePath: String,
    val dateAdded: Long,
    val mimeType: String,
    val title: String,
    val isFavorite: Boolean
)
