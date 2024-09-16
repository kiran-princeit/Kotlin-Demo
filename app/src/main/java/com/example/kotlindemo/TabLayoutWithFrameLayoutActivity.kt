package com.example.kotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.kotlindemo.fragments.AndroidFragment
import com.example.kotlindemo.fragments.JavaFragment
import com.example.kotlindemo.fragments.KotlinFragment
import com.google.android.material.tabs.TabLayout

class TabLayoutWithFrameLayoutActivity : AppCompatActivity() {

    var tabLayout: TabLayout? = null
    var frameLayout: FrameLayout? = null
    var fragment: Fragment? = null
    var fragmentManager: FragmentManager? = null
    var fragmentTransaction: FragmentTransaction? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_layout_with_frame_layout)

        tabLayout = findViewById(R.id.tabLayout)
        frameLayout = findViewById<FrameLayout>(R.id.frameLayout)

        fragment = JavaFragment()
        fragmentManager = supportFragmentManager
        fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction!!.replace(R.id.frameLayout, fragment as JavaFragment)
        fragmentTransaction!!.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction!!.commit()

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

                if (tab?.position == 0) {
                    fragment = JavaFragment()

                } else if (tab?.position == 1) {
                    fragment = AndroidFragment()
                } else if (tab?.position == 2) {
                    fragment = KotlinFragment()
                }
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                ft.replace(R.id.frameLayout, fragment!!)
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                ft.commit()


            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }


        })

    }
}