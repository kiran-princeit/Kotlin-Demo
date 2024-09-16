package com.example.kotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class ViewRetroDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_retro_data)

        val imageView: ImageView = findViewById(R.id.imageView)
        val textview: TextView = findViewById(R.id.textView)
        val textView3: TextView = findViewById(R.id.textView3)

        val name: String? = intent.getStringExtra("name")
        val type: String? = intent.getStringExtra("type")
        val urls: String? = intent.getStringExtra("URL")



        Glide.with(this).load(urls).into(imageView)
        textview.text = name
        textView3.text = type


    }


}
