package com.example.kotlindemo.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.kotlindemo.PlaybackActivity
import com.example.kotlindemo.service.MediaPlaybackService
import com.example.kotlindemo.service.PlaybackService

class MediaButtonReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.getStringExtra("ACTION")
        val serviceIntent = Intent(context, PlaybackService::class.java).apply {
            putExtra("ACTION", action)
        }
        if (context != null) {
            context.startService(serviceIntent)
        }
    }
}