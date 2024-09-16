package com.example.kotlindemo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.kotlindemo.fragments.AudioFragment
import com.example.kotlindemo.fragments.FolderFragment
import com.example.kotlindemo.fragments.ImageFragment
import com.example.kotlindemo.fragments.VideoeFragment
import com.google.android.material.tabs.TabLayout
import org.jetbrains.annotations.Nullable

class GalleryActivity : AppCompatActivity() {

    private lateinit var imageFragment: ImageFragment
    private lateinit var videoFragment: VideoeFragment
    private lateinit var folderFragment: FolderFragment
    private lateinit var audioFragment: AudioFragment


    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var toolbar: Toolbar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)


        tabLayout = findViewById(R.id.tab_layout) as TabLayout
        viewPager = findViewById(R.id.view_pager) as ViewPager


        imageFragment = ImageFragment()
        videoFragment = VideoeFragment()
        folderFragment = FolderFragment()
        audioFragment = AudioFragment()

        toolbar = findViewById(R.id.toolbar_)
        setSupportActionBar(toolbar)

        setUpViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)

    }


    private fun forwardSearchQuery(newText: String?) {
        val currentFragment = (viewPager.adapter as ViewPagerAdapter).getItem(viewPager.currentItem)
        if (currentFragment is SearchableFragment) {
            currentFragment.onSearchQueryChanged(newText)
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
//                    updateToolbarForTab(it.position)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Optionally handle tab unselected
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Optionally handle tab reselected
            }
        })

    }

    private fun setUpViewPager(viewPager: ViewPager) {

        val adapter: ViewPagerAdapter =
            ViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(imageFragment, "Image")
        adapter.addFragment(folderFragment, "Folder")
        adapter.addFragment(videoFragment, "Video")
        adapter.addFragment(audioFragment, "Audio")
        viewPager.setAdapter(adapter)
//        updateToolbarForTab(tabLayout.selectedTabPosition)

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.filter_menu, menu)

        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle the search query submission

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle live text changes
                forwardSearchQuery(newText)
                if (newText != null) {
                    folderFragment.filterFolders(newText)
                }

                return true
            }
        })

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val fragment = supportFragmentManager.findFragmentById(R.id.view_pager)
        when (item.itemId) {
            R.id.filterByDate -> {
                if (fragment is ImageFragment) {
                    fragment.showDateFilterDialog()
                } else if (fragment is FolderFragment) {
                    fragment.showDateFilterDialog()
                } else if (fragment is VideoeFragment) {
                    fragment.showDateFilterDialog()
                }
                return true
            }

            R.id.filterByName -> {
                if (fragment is ImageFragment) {
                    fragment.showSortDialog()
                } else if (fragment is FolderFragment) {
                    fragment.showSortDialog()
                } else if (fragment is VideoeFragment) {
                    fragment.showSortDialog()
                }
                return true
            }

            R.id.action_fav -> {
                startActivity(Intent(this, FavoriteActivity::class.java))

            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun showToolbarIcon(iconId: Int) {
        toolbar.menu.findItem(iconId)?.isVisible = true
    }

    private fun hideToolbarIcon(iconId: Int) {
        toolbar.menu.findItem(iconId)?.isVisible = false
    }

    private fun updateToolbarForTab(position: Int) {
        when (position) {
            0 -> {
                showToolbarIcon(R.id.filterByName)

            }

            1 -> {
                hideToolbarIcon(R.id.action_search)

            }

            else -> {
                showToolbarIcon(R.id.filterByName)
            }
        }
    }

}


