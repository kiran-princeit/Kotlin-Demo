package com.example.kotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar


class ContextMenuActivity : AppCompatActivity() {

    val contact = arrayOf("Asha","Vruti","Neeva","Manya","Daivik","Tinu")

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_context_menu)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val listview = findViewById<ListView>(R.id.listView)
        setSupportActionBar(toolbar)
        toolbar.setTitle("Context Menu")

        val arrayAdapter: ArrayAdapter<String> =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, contact)
        listview.adapter = arrayAdapter
        registerForContextMenu(listview)

    }



    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {

        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)


    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        return when (item!!.itemId) {

            R.id.call -> {
                Toast.makeText(applicationContext, "call code", Toast.LENGTH_LONG).show()
                return true
            }

            R.id.sms -> {
                Toast.makeText(applicationContext, "sms code", Toast.LENGTH_LONG).show()
                return true
            }


            else -> {
                return super.onContextItemSelected(item)
            }
        }


    }
}