package com.example.kotlindemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.Model.NoteDataBaseHelper
import com.example.kotlindemo.adapter.DatabaseAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CRUDActivity : AppCompatActivity() {
    lateinit var rv_Data: RecyclerView

    lateinit var db: NoteDataBaseHelper
    lateinit var databaseAdapter: DatabaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crudactivity)


        val actionbtn: FloatingActionButton = findViewById(R.id.addButton)
        rv_Data = findViewById(R.id.rv_Data)
        db = NoteDataBaseHelper(this)


        databaseAdapter = DatabaseAdapter(db.getAllNotes(), this)
        rv_Data.layoutManager = LinearLayoutManager(this)
        rv_Data.adapter = databaseAdapter

        actionbtn.setOnClickListener(View.OnClickListener {

            startActivity(Intent(this, InsertDataActivity::class.java))

        })

    }

    override fun onResume() {
        super.onResume()
        databaseAdapter.Refreshdata(db.getAllNotes())
    }


}