package com.example.kotlindemo

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.kotlindemo.Database.MediaDatabase
import com.example.kotlindemo.Database.SharedPreferencesUtil
import com.example.kotlindemo.Model.MediaItem
import com.example.kotlindemo.adapter.FavoriteAdapter
import com.example.kotlindemo.adapter.MediaAdapter
import com.example.kotlindemo.fragments.Fav_Audio_Fragment
import com.example.kotlindemo.fragments.Fav_VideoFragment
import com.example.kotlindemo.fragments.FavoriteFragment
import com.example.kotlindemo.fragments.ImageFragment
import com.example.kotlindemo.fragments.VideoeFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.annotations.Nullable
import java.util.Date

class FavoriteActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    private lateinit var favoriteFragment: FavoriteFragment
    private lateinit var favVideofragment: Fav_VideoFragment
    private lateinit var favAudioFragment: Fav_Audio_Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        tabLayout = findViewById(R.id.tab_layout) as TabLayout
        viewPager = findViewById(R.id.view_pager) as ViewPager

        favoriteFragment= FavoriteFragment()
        favVideofragment= Fav_VideoFragment()
        favAudioFragment= Fav_Audio_Fragment()
        setUpViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun setUpViewPager(viewPager: ViewPager) {

        val adapter: ViewPagerAdapter =
            ViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(favoriteFragment, "Image")
        adapter.addFragment(favVideofragment, "Video")
        adapter.addFragment(favAudioFragment, "Audio")
        viewPager.setAdapter(adapter)

    }
    class ViewPagerAdapter : FragmentPagerAdapter {

        private val framentlist1: ArrayList<Fragment> = ArrayList()
        private val framentlisttitle: ArrayList<String> = ArrayList()

        constructor(fm: FragmentManager) : super(fm)


        override fun getCount(): Int {
            return framentlist1.size
        }

        override fun getItem(position: Int): Fragment {
            return framentlist1.get(position)
        }

        fun addFragment(fragment: Fragment, title: String) {
            framentlist1.add(fragment)
            framentlisttitle.add(title)
        }


        @Nullable
        override fun getPageTitle(position: Int): CharSequence {
            return framentlisttitle.get(position)
        }

    }
}


