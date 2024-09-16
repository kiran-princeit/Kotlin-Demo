package com.example.kotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.kotlindemo.Model.Note
import com.example.kotlindemo.Model.NoteDataBaseHelper

class UpdatedataActivity : AppCompatActivity() {

    lateinit var db: NoteDataBaseHelper;

    var noteId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_updatedata)

        val titleUpdate: EditText = findViewById(R.id.titleUpdate)
        val ed_des_update: EditText = findViewById(R.id.ed_des_update)
        val updateNote: ImageView = findViewById(R.id.updateNote)

        db = NoteDataBaseHelper(this)
        noteId = intent.getIntExtra("note_id", -1)

        if (noteId == -1) {
            finish()
            return
        }

        val note = db.getNoteByID(noteId)

        titleUpdate.setText(note.title)
        ed_des_update.setText(note.content)

        updateNote.setOnClickListener(View.OnClickListener {

            val newtitle = titleUpdate.text.toString()
            val newcontent = ed_des_update.text.toString()

            val update_Note = Note(noteId, newtitle, newcontent)
            db.updateNote(update_Note)
            finish()
            Toast.makeText(this, "Note Updated...", Toast.LENGTH_SHORT).show()
        })

    }
}