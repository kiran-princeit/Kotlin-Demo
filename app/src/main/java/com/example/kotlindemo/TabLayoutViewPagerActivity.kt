package com.example.kotlindemo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.kotlindemo.fragments.HomeFragment
import com.example.kotlindemo.fragments.MovieFragment
import com.example.kotlindemo.fragments.SportFragment
import com.google.android.material.tabs.TabLayout
import org.jetbrains.annotations.Nullable
import androidx.fragment.app.Fragment as Fragment1

class TabLayoutViewPagerActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_layout_view_pager)

        val tabLayout = findViewById(R.id.tabLayout) as TabLayout
        val viewPager = findViewById(R.id.viewPager) as ViewPager

        setUpViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)


    }

    private fun setUpViewPager(viewPager: ViewPager) {

        val adapter: ViewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment(), "Home")
        adapter.addFragment(SportFragment(), "Sport")
        adapter.addFragment(MovieFragment(), "Movie")
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

