package com.example.kotlindemo.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.media.app.NotificationCompat.MediaStyle
import androidx.media.session.MediaButtonReceiver

import com.example.kotlindemo.Model.AudioFile
import com.example.kotlindemo.R
import java.io.IOException

class MediaPlaybackService : Service() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var mediaSession: MediaSessionCompat
    private var isPlaying = false
    private val channelId = "music_playback_channel"
    private val notificationId = 1
    private val audioFiles: MutableList<AudioFile> = mutableListOf()
    private var currentTrackIndex = 0

    override fun onCreate() {
        super.onCreate()
        mediaSession = MediaSessionCompat(this, "MediaPlaybackService").apply {
            isActive = true
        }
        mediaPlayer = MediaPlayer().apply {
            setOnCompletionListener {
                playNextTrack()
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            val action = it.getStringExtra("ACTION")
            val trackIndex = it.getIntExtra("TRACK_INDEX", 0)
            audioFiles.clear()
            audioFiles.addAll(it.getParcelableArrayListExtra("AUDIO_FILES") ?: emptyList())
            currentTrackIndex = trackIndex

            when (action) {
                "PLAY" -> playAudio()
                "PAUSE" -> pauseAudio()
                "NEXT" -> playNextTrack()
                "PREVIOUS" -> playPreviousTrack()
            }
        }
        return START_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Music Playback",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Channel for music playback notifications"
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(title: String, artist: String) {
        createNotificationChannel()

        val playPauseAction = if (isPlaying) {
            NotificationCompat.Action(android.R.drawable.ic_media_pause, "Pause", getPendingIntent("PAUSE"))
        } else {
            NotificationCompat.Action(android.R.drawable.ic_media_play, "Play", getPendingIntent("PLAY"))
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(artist)
            .setSmallIcon(R.drawable.iv_music_album) // Replace with your icon
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.iv_music_album)) // Replace with your icon
            .addAction(playPauseAction)
            .addAction(android.R.drawable.ic_media_previous, "Previous", getPendingIntent("PREVIOUS"))
            .addAction(android.R.drawable.ic_media_next, "Next", getPendingIntent("NEXT"))
            .setStyle(MediaStyle().setMediaSession(mediaSession.sessionToken))
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        startForeground(notificationId, notification)
    }

    private fun getPendingIntent(action: String): PendingIntent {
        val intent = Intent(this, MediaButtonReceiver::class.java).apply {
            putExtra("ACTION", action)
        }
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }

    private fun playAudio() {
        if (audioFiles.isNotEmpty() && !isPlaying) {
            val currentTrack = audioFiles[currentTrackIndex]
            try {
                mediaPlayer.reset()
                mediaPlayer.setDataSource(this, currentTrack.uri!!)
                mediaPlayer.prepare()
                mediaPlayer.start()
                isPlaying = true
                showNotification(currentTrack.title ?: "Title", currentTrack.artist ?: "Artist")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun pauseAudio() {
        if (isPlaying) {
            mediaPlayer.pause()
            isPlaying = false
            val currentTrack = audioFiles[currentTrackIndex]
            showNotification(currentTrack.title ?: "Title", currentTrack.artist ?: "Artist")
        }
    }



    private fun playNextTrack() {
        if (currentTrackIndex < audioFiles.size - 1) {
            currentTrackIndex++
            playAudio()
        }
    }

    private fun playPreviousTrack() {
        if (currentTrackIndex > 0) {
            currentTrackIndex--
            playAudio()
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        mediaSession.release()
    }



}
