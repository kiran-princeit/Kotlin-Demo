package com.example.kotlindemo.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.kotlindemo.Model.MediaItem
import com.example.kotlindemo.R
import com.example.kotlindemo.SearchableFragment
import com.example.kotlindemo.adapter.MediaAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class ImageFragment : Fragment(), SearchableFragment {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mediaAdapter: MediaAdapter
    private var mediaItems: List<MediaItem> = listOf()
    private var filteredMediaItems: List<MediaItem> = listOf()

    lateinit var mcontext: Context


    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val imagesGranted = permissions[android.Manifest.permission.READ_MEDIA_IMAGES] ?: false
            val videosGranted = permissions[android.Manifest.permission.READ_MEDIA_VIDEO] ?: false

            if (imagesGranted || videosGranted) {
                loadMedia()
            } else {
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mcontext = requireActivity()
        recyclerView = view.findViewById(R.id.RV_iamge)
        recyclerView.setHasFixedSize(true)

        val mlayoutManager = object : GridLayoutManager(requireContext(), 3) {
            override fun onLayoutChildren(
                recycler: RecyclerView.Recycler,
                state: RecyclerView.State
            ) {
                super.onLayoutChildren(recycler, state)
                spanSizeLookup = object : SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (mediaAdapter.getItemViewType(position)) {
                            MediaItem.TYPE_HEADER -> spanCount // Span full width for headers
                            else -> 1 // Default span size for other items
                        }
                    }
                }
            }
        }

        recyclerView.layoutManager = mlayoutManager

        when {
            // For Android 13 (API level 33) and later
            ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                        requireContext(),
                        android.Manifest.permission.READ_MEDIA_VIDEO
                    ) == PackageManager.PERMISSION_GRANTED -> {
                loadMedia()
            }
            // For Android 10 (API level 29) and later
            ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                loadMedia()
            }

            else -> {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    requestPermissionLauncher.launch(
                        arrayOf(
                            android.Manifest.permission.READ_MEDIA_IMAGES,
                            android.Manifest.permission.READ_MEDIA_VIDEO
                        )
                    )
                } else {
                    requestPermissionLauncher.launch(
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    )
                }
            }
        }


    }


    private fun loadMedia() {
        mediaItems = getMediaItems()
        filteredMediaItems = mediaItems
        updateAdapter()

    }

    @SuppressLint("Range")
    private fun getMediaItems(): List<MediaItem> {
        val mediaItems = mutableListOf<MediaItem>()
        val projection = arrayOf(
            MediaStore.MediaColumns._ID,
            MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.DATE_ADDED,
            MediaStore.MediaColumns.MIME_TYPE,
            MediaStore.Images.Media.DISPLAY_NAME
        )

        val cursor: Cursor? = requireActivity().contentResolver.query(
            MediaStore.Files.getContentUri("external"),
            projection,
            null,
            null,
            "${MediaStore.MediaColumns.DATE_ADDED} DESC"
        )

        cursor?.use {

            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndex(MediaStore.MediaColumns._ID))
                val data = it.getString(it.getColumnIndex(MediaStore.MediaColumns.DATA))
                val dateAdded = it.getLong(it.getColumnIndex(MediaStore.MediaColumns.DATE_ADDED))
                val mimeType = it.getString(it.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE))
                val name =
                    it.getString(it.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME))

//                val uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

                val item =
                    MediaItem(
                        id,
                        data,
                        Date(dateAdded * 1000),
                        mimeType,
                        name,
                        MediaItem.TYPE_MEDIA, MediaItem.Type.IMAGE
                    )
                mediaItems.add(item)
            }
        }

        return mediaItems
    }

    private fun organizeMediaByDate(mediaItems: List<MediaItem>): Map<String, List<MediaItem>> {
        val mediaByDate = LinkedHashMap<String, MutableList<MediaItem>>()
        for (item in mediaItems) {
            val dateKey = getFormattedDate(item.dateAdded)
            if (!mediaByDate.containsKey(dateKey)) {
                mediaByDate[dateKey] = mutableListOf()
            }
            mediaByDate[dateKey]?.add(item)
        }
        return mediaByDate
    }

    private fun getFormattedDate(date: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = date

        val today = Calendar.getInstance()
        val tomorrow = Calendar.getInstance()
        tomorrow.add(Calendar.DAY_OF_YEAR, 1)

        return when {
            calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                    calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) -> "Today"

            calendar.get(Calendar.YEAR) == tomorrow.get(Calendar.YEAR) &&
                    calendar.get(Calendar.DAY_OF_YEAR) == tomorrow.get(Calendar.DAY_OF_YEAR) -> "Tomorrow"

            else -> SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(date)
        }
    }

    private fun updateAdapter() {
        val mediaByDate = organizeMediaByDate(filteredMediaItems)

        val finalList = mutableListOf<MediaItem>()
        for ((date, items) in mediaByDate) {
            finalList.add(
                MediaItem(
                    0,
                    date,
                    Date(),
                    "",
                    "",
                    MediaItem.TYPE_HEADER,
                    MediaItem.Type.IMAGE
                )
            )
            finalList.addAll(items)
        }

        mediaAdapter = MediaAdapter(requireContext(), finalList)
        mediaAdapter.updateData(finalList)
        recyclerView.adapter = mediaAdapter

    }


    fun showDateFilterDialog() {
        // Get the current date
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Create a DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            mcontext,
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                // Handle date selected by user
                val selectedDate = Calendar.getInstance().apply {
                    set(Calendar.YEAR, selectedYear)
                    set(Calendar.MONTH, selectedMonth)
                    set(Calendar.DAY_OF_MONTH, selectedDay)
                }.time

                // Format the selected date
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate)

                // Filter media items
                filterMediaByDate(formattedDate)
            }, year, month, day
        )
        datePickerDialog.show()
    }

    fun showSortDialog() {
        val sortOptions =
            arrayOf("Name (A-Z)", "Name (Z-A)", "Date (Newest first)", "Date (Oldest first)")

        val builder = AlertDialog.Builder(mcontext)
        builder.setTitle("Sort By")
            .setItems(sortOptions) { _, which ->
                when (which) {
                    0 -> sortByNameAscending()
                    1 -> sortByNameDescending()
                    2 -> sortByDateDescending()
                    3 -> sortByDateAscending()
                }
            }
            .show()
    }

    private fun sortByNameAscending() {
        filteredMediaItems = filteredMediaItems.sortedBy { it.title }
        updateAdapter()
        Toast.makeText(requireContext(), "Sorted by Name (A-Z)", Toast.LENGTH_SHORT).show()
    }

    private fun sortByNameDescending() {
        filteredMediaItems = filteredMediaItems.sortedByDescending { it.title }
        updateAdapter()
        Toast.makeText(requireContext(), "Sorted by Name (Z-A)", Toast.LENGTH_SHORT).show()
    }

    private fun sortByDateDescending() {
        filteredMediaItems = filteredMediaItems.sortedByDescending { it.dateAdded }
        updateAdapter()
        Toast.makeText(requireContext(), "Sorted by Date (Newest first)", Toast.LENGTH_SHORT).show()
    }

    private fun sortByDateAscending() {
        filteredMediaItems = filteredMediaItems.sortedBy { it.dateAdded }
        updateAdapter()
        Toast.makeText(requireContext(), "Sorted by Date (Oldest first)", Toast.LENGTH_SHORT).show()
    }

    private fun filterMediaByDate(date: String) {
        val filteredList = mediaItems.filter { item ->
            val itemDate =
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(item.dateAdded)
            itemDate == date
        }
        filteredMediaItems = filteredList
        updateAdapter()
        Toast.makeText(requireContext(), "Filtered by date: $date", Toast.LENGTH_SHORT).show()
    }

    override fun onSearchQueryChanged(query: String?) {
        val lowerCaseQuery = query?.toLowerCase(Locale.getDefault()).orEmpty()
        filteredMediaItems = mediaItems.filter { item ->
            item.title.toLowerCase(Locale.getDefault()).contains(lowerCaseQuery)
        }
        updateAdapter()
    }


}