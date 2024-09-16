package com.example.kotlindemo.other

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import com.example.kotlindemo.Model.AudioFile
import com.example.kotlindemo.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Timer
import java.util.TimerTask

class MusicPlayerBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var mediaPlayer: MediaPlayer // or ExoPlayer
    private lateinit var songName: TextView
    private lateinit var artistName: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var playPauseButton: ImageButton
//    private lateinit var audioFile: AudioFile

//    companion object {
//        private const val ARG_AUDIO_FILE = "arg_audio_file"
//
//        fun newInstance(audioFile: AudioFile): MusicPlayerBottomSheetFragment {
//            val fragment = MusicPlayerBottomSheetFragment()
//            val args = Bundle().apply {
//                putParcelable(ARG_AUDIO_FILE, audioFile)
//            }
//            fragment.arguments = args
//            return fragment
//        }
//    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            audioFile = it.getParcelable(ARG_AUDIO_FILE)
//                ?: throw IllegalArgumentException("AudioFile must be provided")
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_media_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        songName = view.findViewById(R.id.song_name)
        artistName = view.findViewById(R.id.artist_name)
        seekBar = view.findViewById(R.id.seek_bar)
        playPauseButton = view.findViewById(R.id.play_pause_button)

//        songName.text = audioFile.title
//        artistName.text = audioFile.artist

//        setupMediaPlayer(audioFile.uri.toString())
        setupControls()
    }

    private fun setupMediaPlayer(audioFileUrl: String) {
//        mediaPlayer = MediaPlayer().apply {
//            // Initialize media player with a sample URL
//            setDataSource(audioFileUrl)
//            prepare()
//        }

        // Update seekBar max value
        seekBar.max = mediaPlayer.duration
        mediaPlayer.setOnCompletionListener {
            playPauseButton.setImageResource(android.R.drawable.ic_media_play)
        }

        // Update seekBar progress periodically
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                activity?.runOnUiThread {
                    seekBar.progress = mediaPlayer.currentPosition
                }
            }
        }, 0, 1000)
    }

    private fun setupControls() {
        playPauseButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                playPauseButton.setImageResource(android.R.drawable.ic_media_play)
            } else {
                mediaPlayer.start()
                playPauseButton.setImageResource(android.R.drawable.ic_media_pause)
            }
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        view?.findViewById<ImageButton>(R.id.prev_button)?.setOnClickListener {
            // Handle previous button click
        }

        view?.findViewById<ImageButton>(R.id.next_button)?.setOnClickListener {
            // Handle next button click
        }
    }

    override fun onStart() {
        super.onStart()
        // Set the height of the bottom sheet to match the parent view when in expanded mode
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from(bottomSheet!!)
        behavior.peekHeight = 200 // You can adjust this value
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        behavior.isHideable = false

        // Optional: Full-screen mode logic
        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        // Handle the expanded state
                        // Optionally set full screen here
                    }

                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        // Handle the collapsed state
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // Handle the slide offset if needed
            }
        })
    }
}