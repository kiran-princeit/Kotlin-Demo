package com.example.kotlindemo.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.kotlindemo.Model.MediaItem


@Dao
interface MediaItemDao {

    @Query("SELECT * FROM media_items WHERE isFavorite = 1")
    fun getFavoriteMediaItems(): List<MediaItem>

    @Insert
    suspend fun insertMediaItem(mediaItem: MediaItemEntity)

    @Update
    suspend fun updateMediaItem(mediaItem: MediaItemEntity)
}