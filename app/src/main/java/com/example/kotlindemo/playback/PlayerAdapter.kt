package com.example.kotlindemo.playback

import android.media.MediaPlayer
import com.example.kotlindemo.Model.AudioFile

interface  PlayerAdapter {
    fun isMediaPlayer(): Boolean

    fun isPlaying(): Boolean

    fun isReset(): Boolean

    fun getCurrentSong(): AudioFile?

    @PlaybackInfoListener.State
    fun getState(): Int

    fun getPlayerPosition(): Int

    fun getMediaPlayer(): MediaPlayer?
    fun initMediaPlayer()

    fun release()

    fun resumeOrPause()

    fun reset()

    fun instantReset()

    fun skip(isNext: Boolean)

    fun seekTo(position: Int)

    fun setPlaybackInfoListener(playbackInfoListener: PlaybackInfoListener)

    fun registerNotificationActionsReceiver(isRegister: Boolean)


    fun setCurrentSong(song: AudioFile, songs: List<AudioFile>)

    fun onPauseActivity()

    fun onResumeActivity()

}