package com.example.kotlindemo

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView

class VideoViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_view)

        val title: TextView = findViewById(R.id.tv_video_title)
        val videoView: VideoView = findViewById(R.id.video_view)

        val mediaController: MediaController


        val videoUris = intent.getStringArrayListExtra("VIDEO_URIS") ?: arrayListOf()
        val selectedVideo = intent.getStringExtra("SELECTED_VIDEO") ?: ""
        val selectedVideoName = intent.getStringExtra("SELECTED_VIDEO_NAME") ?: ""

        mediaController = MediaController(this)

        title.setText(selectedVideoName)
        val url: String = videoUris.toString()
        videoView.setVideoPath(selectedVideo)

        videoView.setOnPreparedListener(MediaPlayer.OnPreparedListener { mediaPlayer ->
            mediaPlayer.start()
            videoView.start()
        })

        videoView.setMediaController(mediaController)
//        videoView.requestFocus()


    }
}