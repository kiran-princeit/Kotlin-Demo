package com.example.kotlindemo.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.kotlindemo.R

class MyListAdapter(
    private val context: Activity,
    private val title: Array<String>,
    private val desciption: Array<String>,
//    private val imgid: Array<Int>
) : ArrayAdapter<String>(context, R.layout.custom_list, title) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = context.layoutInflater
        val rootview = inflater.inflate(R.layout.custom_list, null, true)
        val titletxt = rootview.findViewById(R.id.title) as TextView
        val decText = rootview.findViewById(R.id.description) as TextView

        titletxt.text = title[position]
        decText.text = desciption[position]

        return rootview
    }


}