package com.example.kotlindemo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.example.kotlindemo.Model.MediaItem
import com.example.kotlindemo.adapter.ImagePagerAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


class ImageViewerActivity : AppCompatActivity() {


    lateinit var iv_delete: ImageView
    lateinit var share: ImageView
    private lateinit var adapter: ImagePagerAdapter
    lateinit var viewPager: ViewPager

    lateinit var imageUris: ArrayList<String>

    lateinit var selectedImage: String

    lateinit var activity: Activity

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_viewer)

        viewPager = findViewById(R.id.viewPager)
        val title: TextView = findViewById(R.id.tv_img_title)
        iv_delete = findViewById(R.id.iv_delete)
        share = findViewById(R.id.iv_share)

        imageUris = intent.getStringArrayListExtra("IMAGE_URIS") ?: arrayListOf()
        selectedImage = intent.getStringExtra("SELECTED_IMAGE") ?: ""
        val selectedImageName = intent.getStringExtra("SELECTED_IMAGE_NAME") ?: ""


        title.setText(selectedImageName)
        // Set up the adapter
        adapter = ImagePagerAdapter(this, imageUris)
        viewPager.adapter = adapter

        val startIndex = imageUris.indexOf(selectedImage)
        viewPager.setCurrentItem(startIndex, false)

        activity = this

        iv_delete.setOnClickListener {

            val uriss = Uri.parse(selectedImage)

//            deleteImage(uriss)
//            delCurrentPageFnc()

//            deleteImageFileFromMediaProvider(this,selectedImage)
//            if (ContextCompat.checkSelfPermission(
//                    this,
//                    android.Manifest.permission.READ_MEDIA_IMAGES
//                )
//                != PackageManager.PERMISSION_GRANTED
//            ) {
//                requestPermissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
//            } else {
//                deleteSelectedImage(selectedImage)
//            }
            lifecycleScope.launch {
                val isDeletionSuccessful = deletePhotoFromInternalStorage(selectedImage)
                if(isDeletionSuccessful) {
//                    loadPhotosFromInternalStorageIntoRecyclerView()
                    Toast.makeText(this@ImageViewerActivity, "Photo successfully deleted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@ImageViewerActivity, "Failed to delete photo", Toast.LENGTH_SHORT).show()
                }
            }

        }


        share.setOnClickListener(View.OnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, Uri.parse(selectedImage))
                type = "image/*"
            }
            startActivity(Intent.createChooser(intent, "Share Image"))
        })
    }

    private fun deleteImage(imagePath: Uri) {
        getContentResolver().delete(imagePath, null, null)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                // Permission granted, proceed with deletion
                deleteSelectedImage(intent.getStringExtra("SELECTED_IMAGE") ?: "")
            } else {
                // Permission denied, handle accordingly
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    fun delCurrentPageFnc() {
        val delIdxVar = viewPager.currentItem

        if (imageUris.size > 1) {
            imageUris.removeAt(delIdxVar)
            adapter.notifyDataSetChanged()
        } else {
            finish()
        }
    }


    private fun deleteSelectedImage(imageUri: String) {
        val contentResolver: ContentResolver = contentResolver
        val mediaId = getMediaStoreIdFromUri(imageUri) ?: return

        val uriToDelete: Uri =
            ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, mediaId)

        try {
            val rowsDeleted = contentResolver.delete(uriToDelete, null, null)
            if (rowsDeleted > 0) {
                // Image deleted successfully
                imageUris.remove(imageUri)
                adapter.notifyDataSetChanged()

                if (imageUris.isEmpty()) {
                    finish()
                } else {
                    val newIndex =
                        if (viewPager.currentItem >= imageUris.size) 0 else viewPager.currentItem
                    viewPager.setCurrentItem(newIndex, false)
                }
            } else {
                Toast.makeText(this, "Failed to delete image", Toast.LENGTH_SHORT).show()
            }
        } catch (e: SecurityException) {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getMediaStoreIdFromUri(imageUri: String): Long? {
        val contentResolver: ContentResolver = contentResolver
        val projection = arrayOf(MediaStore.Images.Media._ID)
        val selection = "${MediaStore.Images.Media.DATA}=?"
        val selectionArgs = arrayOf(imageUri)

        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )

        cursor?.use {
            if (it.moveToFirst()) {
                val idIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                return it.getLong(idIndex)
            }
        }
        return null
    }

    @SuppressLint("Range")
    fun deleteImageFileFromMediaProvider(
        ctxt: Context,
        imageFile: String?
    ): Int {
        if (imageFile == null) return 0
        val cr = ctxt.contentResolver
        // images
        var count = 0
        var imageCursor: Cursor? = null
        try {
            val select = Images.Media.DATA + "=?"
            val selectArgs = arrayOf(imageFile)
            val projection = arrayOf(Images.ImageColumns._ID)
            imageCursor = cr
                .query(Images.Media.EXTERNAL_CONTENT_URI, projection, select, selectArgs, null)

            if (imageUris.isEmpty()) {
                finish()
            } else {
                val newIndex =
                    if (viewPager.currentItem >= imageUris.size) 0 else viewPager.currentItem
                viewPager.setCurrentItem(newIndex, false)
            }

            if (imageCursor!!.count > 0) {
                imageCursor!!.moveToFirst()
                val imagesToDelete: MutableList<Uri> = ArrayList()
                do {
                    val id = imageCursor!!.getString(
                        imageCursor.getColumnIndex(Images.ImageColumns._ID)
                    )
                    imagesToDelete.add(Uri.withAppendedPath(Images.Media.EXTERNAL_CONTENT_URI, id))
                } while (imageCursor!!.moveToNext())
                for (uri in imagesToDelete) {
                    Log.i("t", "attempting to delete: $uri")
                    count += cr.delete(uri, null, null)
                }


            }
        } catch (e: Exception) {
            Log.e("t", e.toString())
        } finally {
            imageCursor?.close()
        }
        val f = File(imageFile)
        if (f.exists()) {
            f.delete()
        }
        return count
    }

    private val REQUEST_PERM_DELETE = 8

    @RequiresApi(api = Build.VERSION_CODES.R)
    fun requestDeletePermission(context: Context, uri_list: List<Uri?>?) {
        try {
            val editPendingIntent = MediaStore.createDeleteRequest(
                context.contentResolver,
                uri_list!!
            )
            startIntentSenderForResult(
                editPendingIntent.intentSender,
                REQUEST_PERM_DELETE,
                null,
                0,
                0,
                0
            )
        } catch (e: SendIntentException) {
            Log.i(
                "zakaria_mediaD", """   requestDeletePermission error:
${e.message}"""
            )
        }
    }


    //5 process done
    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_PERM_DELETE -> if (resultCode == RESULT_OK) {
                Toast.makeText(
                    this@ImageViewerActivity,
                    "Deleted successfully! Refreshing...",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(this@ImageViewerActivity, "Failed to delete!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private suspend fun deletePhotoFromInternalStorage(filename: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                deleteFile(filename)
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }




}