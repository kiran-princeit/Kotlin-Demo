package com.example.kotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.kotlindemo.Model.Note
import com.example.kotlindemo.Model.NoteDataBaseHelper

class InsertDataActivity : AppCompatActivity() {
    lateinit var db: NoteDataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_data)

        db = NoteDataBaseHelper(this)

        val SaveNote: ImageView = findViewById(R.id.SaveNote)
        val titleED: EditText = findViewById(R.id.titleEditText)
        val desEditText: EditText = findViewById(R.id.desEditText)

        SaveNote.setOnClickListener {
            val title = titleED.text.toString()
            val content = desEditText.text.toString()
            val note = Note(1, title, content)
            db.insertNote(note)
            finish()
            Toast.makeText(this, " Note Save", Toast.LENGTH_SHORT).show()
        }


    }
}