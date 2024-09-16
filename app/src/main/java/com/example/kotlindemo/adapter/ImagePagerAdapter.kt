package com.example.kotlindemo.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.kotlindemo.R

class ImagePagerAdapter(
    private val context: Context,
    private val imageUris: ArrayList<String>
) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = LayoutInflater.from(context)
            .inflate(R.layout.item_image_viewer, container, false) as ImageView
        Glide.with(context)
            .load(imageUris[position])
            .into(imageView)
        container.addView(imageView)
        return imageView
    }

    override fun getCount(): Int = imageUris.size

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object`


    fun removeItem(position: Int) {
//        if (position >= 0 && position < imageUris.size) {
            imageUris.removeAt(position)
            notifyDataSetChanged()
//            Log.d("ImagePagerAdapter", "Removed item at position $position")
//        } else {
//            Log.d("ImagePagerAdapter", "Invalid position $position")
//        }
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }



}
