package com.example.kotlindemo.Pagination.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.Pagination.model.Scheme
import com.example.kotlindemo.R

class SchemeAdapter(val schemes: List<Scheme>) : RecyclerView.Adapter<SchemeAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val schmeName: TextView = itemView.findViewById(R.id.schemeName)
        val schemeDescription: TextView = itemView.findViewById(R.id.schemeDescription)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.volley_page_list, parent, false)
        )
    }

    override fun getItemCount(): Int = schemes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val scheme = schemes[position]
        holder.schmeName.text = scheme.name
        holder.schemeDescription.text = scheme.benefits

    }
}