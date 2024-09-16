package com.example.kotlindemo.adapter

import android.content.Context
import android.content.Intent
import android.service.autofill.UserData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.Model.Contact
import com.example.kotlindemo.R
import com.example.kotlindemo.ViewContactActivity

class ContactAdapter(private val mContext: Context, private val uderData: List<Contact>) :
    RecyclerView.Adapter<ContactAdapter.MyViewHolder>() {


    class MyViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val imageView: ImageView = itemView.findViewById(R.id.iv_image)
        val tv_name: TextView = itemView.findViewById(R.id.tv_name)
        val tv_number: TextView = itemView.findViewById(R.id.tv_number)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.contact_list_item, parent, false)
        )

    }

    override fun getItemCount(): Int {
        return uderData.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val user = uderData[position]

        holder.imageView.setImageResource(user.userProfile)
        holder.tv_name.setText(user.userName)
        holder.tv_number.setText(user.userContact)

        holder.itemView.setOnClickListener(View.OnClickListener {

            val intent = Intent(mContext, ViewContactActivity::class.java)
            intent.putExtra("name", user.userName)
            intent.putExtra("contact", user.userContact)
            intent.putExtra("profile", user.userProfile)
            mContext.startActivity(intent)


        })

    }
}