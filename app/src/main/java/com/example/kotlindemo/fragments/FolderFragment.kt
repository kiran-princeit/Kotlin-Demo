package com.example.kotlindemo.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.FolderImageActivity
import com.example.kotlindemo.Model.FolderItem
import com.example.kotlindemo.Model.MediaItem
import com.example.kotlindemo.R
import com.example.kotlindemo.adapter.FolderAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class FolderFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var folderAdapter: FolderAdapter
    private var filteredFolders: List<FolderItem> = listOf()
    private var folders: List<FolderItem> = listOf()
    private var selectedDate: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_folder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.RV_folder)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        loadMedia()
    }

    @SuppressLint("Range")
    private fun loadMedia() {
        val folderMap = mutableMapOf<String, MutableList<MediaItem>>()

        // Query for images
        val imageProjection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATA,
            MediaStore.MediaColumns.DATE_ADDED,
            MediaStore.MediaColumns.MIME_TYPE

        )
        val imageCursor = requireActivity().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            imageProjection,
            null,
            null,
            null
        )

        imageCursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val dataColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            val dateAdd = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
            val mimeType = it.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)

            while (it.moveToNext()) {
                val id = it.getInt(idColumn)
                val name = it.getString(nameColumn)
                val data = it.getString(dataColumn)
                val mime_type = it.getString(mimeType)
                val date = it.getLong(dateAdd)
//                val uri =
//                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                val folder = getFolderName(data)

                if (folder !in folderMap) {
                    folderMap[folder] = mutableListOf()
                }
//                folderMap[folder]?.add(MediaItem(id, name, MediaItem.Type.IMAGE))
                folderMap[folder]?.add(
                    MediaItem(
                        id,
                        data,
                        Date(date * 1000),
                        mime_type,
                        name,
                        MediaItem.TYPE_MEDIA, MediaItem.Type.IMAGE
                    )
                )
            }
        }

        // Query for videos
        val videoProjection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DATA,
            MediaStore.MediaColumns.DATE_ADDED,
            MediaStore.MediaColumns.MIME_TYPE
        )
        val videoCursor = requireActivity().contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            videoProjection,
            null,
            null,
            null
        )

        videoCursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val nameColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val dataColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            val dateAdd = it.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)
            val mimeType = it.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE)


            while (it.moveToNext()) {
                val id = it.getInt(idColumn)
                val name = it.getString(nameColumn)
                val data = it.getString(dataColumn)
                val mime_type = it.getString(mimeType)
                val date = it.getLong(dateAdd)
//                val uri =
//                    ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
                val folder = getFolderName(data)

                if (folder !in folderMap) {
                    folderMap[folder] = mutableListOf()
                }
//                folderMap[folder]?.add(FolderItem(id, name, MediaItem.Type.VIDEO))

                folderMap[folder]?.add(
                    MediaItem(
                        id,
                        data,
                        Date(date * 1000),
                        mime_type,
                        name,
                        MediaItem.TYPE_MEDIA, MediaItem.Type.VIDEO
                    )
                )
            }
        }

        folders =
            folderMap.map { (folderName, mediaItems) -> FolderItem(folderName, mediaItems) }

        folderAdapter = FolderAdapter(requireActivity(), folders) { folder ->
            openFolder(folder)

        }
        recyclerView.adapter = folderAdapter
    }

    private fun getFolderName(filePath: String): String {
        return filePath.substringBeforeLast("/")
    }

    private fun openFolder(folder: FolderItem) {
        requireActivity().startActivity(
            Intent(
                requireActivity(),
                FolderImageActivity::class.java
            ).putExtra("folder_name", folder.name)
        )
    }
//    fun filterFolders(query: String) {
//        filteredFolders = if (query.isEmpty()) {
//            folders
//        } else {
//            folders.filter { it.name.contains(query, ignoreCase = true) }
//        }
//        folderAdapter.updateFolders(filteredFolders)
//    }


    fun showDateFilterDialog() {
        // Get the current date
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Create a DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            requireContext(),
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
//                filterMediaByDate(formattedDate)
            }, year, month, day
        )
        datePickerDialog.show()
    }

    fun showSortDialog() {
        val sortOptions =
            arrayOf("Name (A-Z)", "Name (Z-A)", "Date (Newest first)", "Date (Oldest first)")

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Sort By")
            .setItems(sortOptions) { _, which ->
                when (which) {
                    0 -> sortFoldersByNameAscending()
                    1 -> sortFoldersByNameDescending()
                    2 -> sortFoldersByDateNewest()
                    3 -> sortFoldersByDateOldest()
                }
            }
            .show()
    }

    private fun sortFoldersByNameAscending() {
        filteredFolders = filteredFolders.sortedBy { it.name }
        folderAdapter.updateFolders(filteredFolders)
    }

    private fun sortFoldersByNameDescending() {
        filteredFolders = filteredFolders.sortedByDescending { it.name }
        folderAdapter.updateFolders(filteredFolders)
    }


    private fun sortFoldersByDateNewest() {
        filteredFolders = filteredFolders.sortedByDescending { folder ->
            folder.mediaItems.maxOfOrNull { it.dateAdded } ?: Date()
        }
        folderAdapter.updateFolders(filteredFolders)
    }

    private fun sortFoldersByDateOldest() {
        filteredFolders = filteredFolders.sortedBy { folder ->
            folder.mediaItems.minOfOrNull { it.dateAdded } ?: Date()
        }
        folderAdapter.updateFolders(filteredFolders)
    }



//    private fun filterMediaByDate(date: String) {
//        val filteredList = folders.filter { item ->
//            val itemDate =
//                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(item.dateAdded)
//            itemDate == date
//        }
//        filteredFolders = filteredList
//        folderAdapter.updateFolders(filteredFolders)
//        Toast.makeText(requireContext(), "Filtered by date: $date", Toast.LENGTH_SHORT).show()
//    }

    fun filterFolders(query: String) {
        filteredFolders = folders.filter { folder ->
            val matchesName = folder.name.contains(query, ignoreCase = true)
            val matchesDate = selectedDate?.let {
                folder.mediaItems.any { item ->
                    val itemDate = item.dateAdded
                    itemDate.after(it) && itemDate.before(Calendar.getInstance().time)
                }
            } ?: true
            matchesName && matchesDate
        }
        folderAdapter.updateFolders(filteredFolders)
    }

}