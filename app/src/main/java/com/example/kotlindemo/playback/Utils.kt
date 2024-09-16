package com.example.kotlindemo.playback

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import com.example.kotlindemo.R
import java.io.ByteArrayInputStream
import java.io.InputStream

object Utils {
    fun songArt(path: String, context: Context): Bitmap {
        val retriever = MediaMetadataRetriever()
        val inputStream: InputStream
        retriever.setDataSource(path)
        if (retriever.embeddedPicture != null) {
            inputStream = ByteArrayInputStream(retriever.embeddedPicture)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            retriever.release()
            return bitmap
        } else {
            return getLargeIcon(context)
        }
    }

    private fun getLargeIcon(context: Context): Bitmap {
        return BitmapFactory.decodeResource(context.resources, R.drawable.iv_music_album)
    }

}