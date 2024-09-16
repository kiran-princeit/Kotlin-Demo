package com.example.kotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast

class SeekBarActivity : AppCompatActivity() {

    var seekBarNormal: SeekBar? = null
    var seekBarDiscrete: SeekBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seek_bar)

        seekBarNormal = findViewById(R.id.seekbar_Default)
        seekBarDiscrete = findViewById(R.id.seekbar_Discrete)

        seekBarNormal?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

                Toast.makeText(applicationContext, "seekbar progress: $p1", Toast.LENGTH_SHORT).show()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

                Toast.makeText(applicationContext, "seekbar touch started!", Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

                Toast.makeText(applicationContext, "seekbar touch stopped!", Toast.LENGTH_SHORT)
                    .show()
            }


        })

        seekBarDiscrete?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Toast.makeText(
                    applicationContext,
                    "discrete seekbar progress: $p1",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                Toast.makeText(
                    applicationContext,
                    "discrete seekbar touch started!",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                Toast.makeText(
                    applicationContext,
                    "discrete seekbar touch stopped",
                    Toast.LENGTH_SHORT
                ).show()
            }


        })

    }
}