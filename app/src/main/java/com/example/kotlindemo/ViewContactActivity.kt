package com.example.kotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class ViewContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_contact)

        val image_pro: ImageView = findViewById(R.id.image_pro)
        val text_name: TextView = findViewById(R.id.text_name)
        val text_number: TextView = findViewById(R.id.text_number)

        val contactname: String? = intent.getStringExtra("name")
        val contactNumber: String? = intent.getStringExtra("contact")
        val contactPro: Int = intent.getIntExtra("profile", R.drawable.user)

        image_pro.setImageResource(contactPro)
        text_name.text = contactname
        text_number.text = contactNumber


    }
}