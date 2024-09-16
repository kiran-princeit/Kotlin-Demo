package com.example.kotlindemo

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.support.v4.media.session.MediaSessionCompat
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.media.session.MediaButtonReceiver.handleIntent
import com.bumptech.glide.Glide
import com.example.kotlindemo.Model.AudioFile
import com.example.kotlindemo.receiver.MediaButtonReceiver
import com.example.kotlindemo.service.MediaPlaybackService

private const val NOTIFICATION_ID = 1
private const val CHANNEL_ID = "music_playback_channel"


class PlaybackActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private var isPlaying = false
    private lateinit var seekBar: SeekBar
    private lateinit var playPauseButton: ImageView
    private lateinit var songTitle: TextView
    private lateinit var artistName: TextView
    private lateinit var albumArt: ImageView
    private lateinit var currentTime: TextView
    private lateinit var totalTime: TextView

    private lateinit var audioFiles: List<AudioFile>
    private var currentTrackIndex: Int = 0
    private lateinit var mediaSession: MediaSessionCompat


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playback)

        seekBar = findViewById(R.id.audio_seekbar)
        playPauseButton = findViewById(R.id.play_pause_button)
        songTitle = findViewById(R.id.audio_title)
        artistName = findViewById(R.id.audio_artist)
        albumArt = findViewById(R.id.album_art_audio)
        currentTime = findViewById(R.id.start_duration)
        totalTime = findViewById(R.id.end_duration)

        val audioUriString = intent.getStringExtra("AUDIO_URI") ?: return
        val audioTitle = intent.getStringExtra("TITLE") ?: "Unknown Title"
        val audioArtist = intent.getStringExtra("ARTIST") ?: "Unknown Artist"

        // Initialize audio files and current track index from intent
        audioFiles = intent.getParcelableArrayListExtra("AUDIO_FILES") ?: listOf()
        currentTrackIndex = intent.getIntExtra("CURRENT_TRACK_INDEX", 0)

        // Ensure the index is within bounds
        if (currentTrackIndex < 0 || currentTrackIndex >= audioFiles.size) {
            finish() // Exit activity if invalid index
            return
        }
        mediaSession = MediaSessionCompat(this, "PlaybackActivity").apply {
            isActive = true
        }


        // Set up the media player with the current track
        val audioUri = Uri.parse(audioUriString)
        setupMediaPlayer(audioUri)
        updateUI(audioTitle, audioArtist)

        // Set up button listeners
        playPauseButton.setOnClickListener {
            if (isPlaying) {
                startPlaybackService("PAUSE")
               pauseAudio()
            } else {
                startPlaybackService("PLAY")
                playAudio()
            }
        }

        findViewById<ImageView>(R.id.previous_button).setOnClickListener {
            startPlaybackService("PREVIOUS")
            playPreviousTrack()
        }

        findViewById<ImageView>(R.id.next_button).setOnClickListener {
            startPlaybackService("NEXT")
            playNextTrack()
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.let {
                        if (it.isPlaying) {
                            try {
                                it.seekTo(progress)
                            } catch (e: IllegalStateException) {
                                // Handle the exception if it occurs
                                e.printStackTrace()
                            }
                        }
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })


        // Do your desire work here
        createNotificationChannel()
        showNotification()
    }

    private fun setupMediaPlayer(audioUri: Uri) {
        mediaPlayer = MediaPlayer().apply {
            setDataSource(this@PlaybackActivity, audioUri)
            prepare()
            seekBar.max = duration
            totalTime.text = formatTime(duration)
            updateSeekBar()
        }
    }

    private fun updateUI(title: String, artist: String) {
        songTitle.text = title
        artistName.text = artist
        loadAlbumArt()
    }

    private fun playAudio() {
        mediaPlayer?.let {
            try {
                it.start()
                playPauseButton.setImageResource(android.R.drawable.ic_media_pause)
                isPlaying = true
                showNotification()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
        }
    }

    private fun pauseAudio() {
        mediaPlayer?.let {
            try {
                it.pause()
                playPauseButton.setImageResource(android.R.drawable.ic_media_play)
                isPlaying = false
                showNotification()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
        }
    }


    private fun updateSeekBar() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                mediaPlayer?.let { mp ->
                    try {
                        if (mp.isPlaying) {
                            seekBar.progress = mp.currentPosition
                            currentTime.text = formatTime(mp.currentPosition)
                        }
                    } catch (e: IllegalStateException) {
                        e.printStackTrace()
                        // Handle the exception if it occurs
                    }
                    handler.postDelayed(this, 1000)
                }
            }
        }, 0)
    }

    private fun formatTime(milliseconds: Int): String {
        val minutes = (milliseconds / 1000) / 60
        val seconds = (milliseconds / 1000) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun loadAlbumArt() {
        audioFiles[currentTrackIndex].let { audioFile ->
            val albumId = audioFile.uri?.let { getAlbumId(it) }
            val albumArtUri = albumId?.let { getAlbumArtUri(it) }
            if (albumArtUri != null) {
                Glide.with(this)
                    .load(albumArtUri)
                    .placeholder(R.drawable.iv_music_album)
                    .into(albumArt)
            }
        }
    }

    private fun getAlbumId(uri: Uri): Long {
        val projection = arrayOf(MediaStore.Audio.Media.ALBUM_ID)
        val cursor = contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                return it.getLong(it.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID))
            }
        }
        return -1L
    }

    private fun getAlbumArtUri(albumId: Long): Uri? {
        val albumUri =
            ContentUris.withAppendedId(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, albumId)
        val projection = arrayOf(MediaStore.Audio.Albums.ALBUM_ART)
        val cursor = contentResolver.query(albumUri, projection, null, null, null)

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


    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        mediaSession.release()
        NotificationManagerCompat.from(this).cancel(NOTIFICATION_ID)
    }




    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Music Playback",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Channel for music playback notifications"
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification() {
        val playPauseAction = if (isPlaying) {
            NotificationCompat.Action(android.R.drawable.ic_media_pause, "Pause", getPendingIntent("PAUSE"))
        } else {
            NotificationCompat.Action(android.R.drawable.ic_media_play, "Play", getPendingIntent("PLAY"))
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(songTitle.text)
            .setContentText(artistName.text)
            .setSmallIcon(R.drawable.iv_music_album) // Replace with your app's icon
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.iv_music_album)) // Replace with your album art
            .addAction(android.R.drawable.ic_media_previous, "Previous", getPendingIntent("PREVIOUS"))
            .addAction(playPauseAction)
            .addAction(android.R.drawable.ic_media_next, "Next", getPendingIntent("NEXT"))
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(0, 1, 2)
                    .setMediaSession(mediaSession.sessionToken)
            )
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
        startPlaybackService("UPDATE_NOTIFICATION")


        NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, notification)
    }



    private fun getPendingIntent(action: String): PendingIntent {
        val intent = Intent(this, MediaButtonReceiver::class.java).apply {
            putExtra("ACTION", action)
        }
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }

    private fun playPreviousTrack() {
        if (currentTrackIndex > 0) {
            currentTrackIndex--
            val previousTrack = audioFiles[currentTrackIndex]
            previousTrack.uri?.let { setupMediaPlayer(it) }
            previousTrack.artist?.let { previousTrack.title?.let { it1 -> updateUI(it1, it) } }
            if (isPlaying) {
                playAudio()
            }
            showNotification()
        }
    }

    private fun playNextTrack() {
        if (currentTrackIndex < audioFiles.size - 1) {
            currentTrackIndex++
            val nextTrack = audioFiles[currentTrackIndex]
            nextTrack.uri?.let { setupMediaPlayer(it) }
            nextTrack.title?.let { nextTrack.artist?.let { it1 -> updateUI(it, it1) } }
            if (isPlaying) {
                playAudio()
            }
            showNotification()
        }
    }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        val action = intent?.action ?: return
        when (action) {
            "PLAY" -> playAudio()
            "PAUSE" -> pauseAudio()
            "NEXT" -> playNextTrack()
            "PREVIOUS" -> playPreviousTrack()
        }
    }
    private fun startPlaybackService(action: String) {
        val intent = Intent(this, MediaPlaybackService::class.java).apply {
            putExtra("ACTION", action)
        }
        startService(intent)
    }
}



