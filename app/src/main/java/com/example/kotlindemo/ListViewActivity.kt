package com.example.kotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.example.kotlindemo.adapter.MyListAdapter

class ListViewActivity : AppCompatActivity() {


    val language = arrayOf<String>(
        "C",
        "C++",
        "Java",
        ".Net",
        "Kotlin",
        "Ruby",
        "Rails",
        "Python",
        "Java Script",
        "Php",
        "Ajax",
        "Perl",
        "Hadoop"
    )

    val description = arrayOf<String>(
        "C programming is considered as the base for other programming languages",
        "C++ is an object-oriented programming language.",
        "Java is a programming language and a platform.",
        ".NET is a framework which is used to develop software applications.",
        "Kotlin is a open-source programming language, used to develop Android apps and much more.",
        "Ruby is an open-source and fully object-oriented programming language.",
        "Ruby on Rails is a server-side web application development framework written in Ruby language.",
        "Python is interpreted scripting  and object-oriented programming language.",
        "JavaScript is an object-based scripting language.",
        "PHP is an interpreted language, i.e., there is no need for compilation.",
        "AJAX allows you to send and receive data asynchronously without reloading the web page.",
        "Perl is a cross-platform environment used to create network and server-side applications.",
        "Hadoop is an open source framework from Apache written in Java."
    )




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)

        val listView: ListView = findViewById(R.id.listView)

        val listviewAdapter = MyListAdapter(this, language, description)
        listView.adapter = listviewAdapter

        listView.setOnItemClickListener { adapterView, view, i, l ->

            val itemAtPos =adapterView.getItemAtPosition(i)
            val itemIdPos =adapterView.getItemAtPosition(i)
            Toast.makeText(this, "Click on item at $itemAtPos its item id $itemIdPos", Toast.LENGTH_LONG).show()

        }

    }
}