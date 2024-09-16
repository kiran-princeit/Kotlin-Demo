package com.example.kotlindemo.fragments

import android.content.ContentUris
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.Model.AudioFile
import com.example.kotlindemo.PlaybackActivity
import com.example.kotlindemo.R
import com.example.kotlindemo.adapter.MusicAdapter


class AudioFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var musicAdapter: MusicAdapter
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_audio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.RV_audio)
        loadAudioFiles()
    }

    private fun loadAudioFiles() {
        val audioList = mutableListOf<AudioFile>()

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM_ID
        )
        val cursor = requireContext().contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
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

                audioList.add(AudioFile(id, title, duration, size, artist, uri, albumArtUri,true))
            }
        }

        // Pass the list to your adapter
        setupRecyclerView(audioList)

    }

    private fun setupRecyclerView(audioList: MutableList<AudioFile>) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        musicAdapter = MusicAdapter(requireContext(), audioList) { audioFile ->
            val currentTrackIndex = audioList.indexOf(audioFile)

            val intent = Intent(requireContext(), PlaybackActivity::class.java).apply {
                putExtra("AUDIO_URI", audioFile.uri.toString())
                putExtra("TITLE", audioFile.title)
                putExtra("ARTIST", audioFile.artist)
                putParcelableArrayListExtra("AUDIO_FILES", ArrayList(audioList))
                putExtra("CURRENT_TRACK_INDEX", currentTrackIndex)
            }
            startActivity(intent)
        }
        recyclerView.adapter = musicAdapter
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
}