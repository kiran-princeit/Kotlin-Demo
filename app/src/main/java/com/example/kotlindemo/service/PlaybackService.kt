package com.example.kotlindemo.service

import android.app.Service
import android.app.Service.START_STICKY
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationManagerCompat
import com.example.kotlindemo.Model.AudioFile
import com.example.kotlindemo.playback.MusicNotificationManager.Companion.NOTIFICATION_ID

class PlaybackService : Service() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var mediaSession: MediaSessionCompat

    lateinit var audioFiles: List<AudioFile>

     var currentTrackIndex = 0

    override fun onCreate() {
        super.onCreate()
        mediaSession = MediaSessionCompat(this, "PlaybackService").apply {
            setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS)
            isActive = true
        }
        mediaPlayer = MediaPlayer() // Initialize media player
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.getStringExtra("ACTION")
        when (action) {
            "PLAY" -> playAudio()
            "PAUSE" -> pauseAudio()
            "PREVIOUS" -> playPreviousTrack()
            "NEXT" -> playNextTrack()
        }
        return START_STICKY
    }

    open fun playAudio() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }

    private fun pauseAudio() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    private fun playPreviousTrack() {
        if (currentTrackIndex < audioFiles.size - 1) {
            currentTrackIndex++
            playAudio()
        }
    }

    private fun playNextTrack() {
        // Implement logic to play next track
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        mediaSession.release()
        NotificationManagerCompat.from(this).cancel(NOTIFICATION_ID)
    }
}