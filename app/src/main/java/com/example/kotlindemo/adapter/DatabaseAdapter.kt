package com.example.kotlindemo.adapter

import android.content.Context
import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.Model.Note
import com.example.kotlindemo.Model.NoteDataBaseHelper
import com.example.kotlindemo.R
import com.example.kotlindemo.UpdatedataActivity

class DatabaseAdapter(private var notes: List<Note>, val context: Context) :
    RecyclerView.Adapter<DatabaseAdapter.ViewHolder>() {


    private val db: NoteDataBaseHelper = NoteDataBaseHelper(context)

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextview: TextView = view.findViewById(R.id.titleTextview)
        val contentTextview: TextView = view.findViewById(R.id.contentTextview)
        val img_edit: ImageView = view.findViewById(R.id.img_edit)
        val img_delete: ImageView = view.findViewById(R.id.img_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.database_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notList = notes[position]

        holder.titleTextview.text = notList.title
        holder.contentTextview.text = notList.content

        holder.img_edit.setOnClickListener(View.OnClickListener {

            context.startActivity(
                Intent(
                    context,
                    UpdatedataActivity::class.java
                ).putExtra("note_id", notList.id)
            )

        })

        holder.img_delete.setOnClickListener(/* l = */ View.OnClickListener {

            db.deleteNote(notList.id)
            Refreshdata(db.getAllNotes())
            Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT).show()

        })
    }

    fun Refreshdata(noteList: List<Note>) {

        notes = noteList
        notifyDataSetChanged()


    }
}