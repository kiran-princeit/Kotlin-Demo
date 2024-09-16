package com.example.kotlindemo

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.kotlindemo.Pagination.PaginationVolleyActivity
import com.example.kotlindemo.nestedRecyclerView.NestedRecyclerViewActivity
import java.util.Locale

class OptinActivity : AppCompatActivity() {

    val REQUEST_ID_MULTIPLE_PERMISSIONS = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_optin)


        val btnlistview: Button = findViewById(R.id.btn_listview)
        val btnwebview: Button = findViewById(R.id.btn_webview)
        val btn_seekbar: Button = findViewById(R.id.btn_seekbar)
        val btn_tablayout: Button = findViewById(R.id.btn_tablayout)
        val btn_alertDialog: Button = findViewById(R.id.btn_alertDialog)
        val btn_context_menu: Button = findViewById(R.id.btn_context_menu)
        val options_menu: Button = findViewById(R.id.options_menu)
        val tab_with_viewpager: Button = findViewById(R.id.tab_with_viewpager)
        val dom_parser: Button = findViewById(R.id.dom_parser)
        val recyclerview: Button = findViewById(R.id.recyclerview)
        val retrofit: Button = findViewById(R.id.retrofit)
        val nested_rv: Button = findViewById(R.id.nested_rv)
        val volley_data: Button = findViewById(R.id.volley_data)
        val btn_lan: Button = findViewById(R.id.btn_lan)
        val btn_crud: Button = findViewById(R.id.btn_crud)
        val btn_pagination: Button = findViewById(R.id.btn_pagination)
        val btn_array: Button = findViewById(R.id.btn_array)
        val btn_gallery: Button = findViewById(R.id.btn_gallery)
        val btn_pagination_volly: Button = findViewById(R.id.btn_pagination_volly)

        btn_array.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ArraysActivity::class.java)
            startActivity(intent)
        })
        btnlistview.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ListViewActivity::class.java)
            startActivity(intent)
        })

        btnwebview.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, WebviewActivity::class.java)
            startActivity(intent)
        })
        btn_seekbar.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, SeekBarActivity::class.java)
            startActivity(intent)
        })

        btn_tablayout.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, TabLayoutWithFrameLayoutActivity::class.java)
            startActivity(intent)
        })
        btn_alertDialog.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, AlertDialogActivity::class.java)
            startActivity(intent)
        })
        btn_context_menu.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ContextMenuActivity::class.java)
            startActivity(intent)
        })
        options_menu.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, OptionMenuActivity::class.java)
            startActivity(intent)
        })
        tab_with_viewpager.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, TabLayoutViewPagerActivity::class.java)
            startActivity(intent)
        })
        dom_parser.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, DomParserActivity::class.java)
            startActivity(intent)
        })
        recyclerview.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, RecyclerViewActivity::class.java)
            startActivity(intent)
        })
        retrofit.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, RetrofitDataFetchingActivity::class.java)
            startActivity(intent)
        })
        nested_rv.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, NestedRecyclerViewActivity::class.java)
            startActivity(intent)
        })
//        volley_data.setOnClickListener(View.OnClickListener {
//            val intent = Intent(this, VolleyDataFetchingActvity::class.java)
//            startActivity(intent)
//        })
        volley_data.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, VolleyActvity::class.java)
            startActivity(intent)
        })

        btn_lan.setOnClickListener(View.OnClickListener {
            changeLanguage()
        })
        btn_crud.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, CRUDActivity::class.java))
        })

        btn_pagination.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, PaginationActivity::class.java))
        })

        btn_gallery.setOnClickListener(View.OnClickListener {
            if (checkAndRequestPermissions()) {

                startActivity(Intent(this, GalleryActivity::class.java))

            } else {
                // Call Again :
                checkAndRequestPermissions();
            }

        })


        btn_pagination_volly.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, PaginationVolleyActivity::class.java))
        })

    }

//    private fun checkAndRequestPermissions() {
//        val permissions = mutableListOf<String>()
//        // Check for Android 13 specific permissions
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (ContextCompat.checkSelfPermission(
//                    this,
//                    android.Manifest.permission.READ_MEDIA_IMAGES
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                permissions.add(android.Manifest.permission.READ_MEDIA_IMAGES)
//            }
//            if (ContextCompat.checkSelfPermission(
//                    this,
//                    android.Manifest.permission.READ_MEDIA_VIDEO
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                permissions.add(android.Manifest.permission.READ_MEDIA_VIDEO)
//            }
//            if (ContextCompat.checkSelfPermission(
//                    this,
//                    android.Manifest.permission.READ_MEDIA_AUDIO
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                permissions.add(android.Manifest.permission.READ_MEDIA_AUDIO)
//            }
//        } else {
//            if (ContextCompat.checkSelfPermission(
//                    this,
//                    android.Manifest.permission.READ_EXTERNAL_STORAGE
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                permissions.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
//            }
//            if (ContextCompat.checkSelfPermission(
//                    this,
//                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                permissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//            }
//
//        }
//
//        if (permissions.isNotEmpty()) {
//            ActivityCompat.requestPermissions(this, permissions.toTypedArray(), 100)
//        } else {
//            // Permissions are already granted, move to the next activity
//            startActivity(Intent(this, GalleryActivity::class.java))
//        }
//    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == 100) {
//            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
//                // Permissions granted, move to the next activity
//                startActivity(Intent(this, GalleryActivity::class.java))
//            } else {
//                // Permissions denied, show a message or handle appropriately
//                Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

    private fun changeLanguage() {

        val lanList = arrayOf<String>("Gujarati", "Hindi")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose Language")
        builder.setSingleChoiceItems(lanList, -1) { dialog, which ->
            if (which == 0) {
                setLocal("gu")
                recreate()

            } else if (which == 1) {
                setLocal("hi")
                recreate()
            }
        }
        builder.show()

    }

    private fun setLocal(s: String) {
        val locale = Locale(s)
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.locale = locale
        baseContext.resources.updateConfiguration(configuration, resources.displayMetrics)

    }


    private fun checkAndRequestPermissions(): Boolean {
        val permissionReadExternalStorage: Int
        val permissionWriteExternalStorage: Int

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionReadExternalStorage =
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
            permissionWriteExternalStorage =
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO)
        } else {
            permissionReadExternalStorage =
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            permissionWriteExternalStorage =
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        val listPermissionsNeeded = mutableListOf<String>()
        if (permissionWriteExternalStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Manifest.permission.READ_MEDIA_AUDIO
                else Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }

        if (permissionReadExternalStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Manifest.permission.READ_MEDIA_IMAGES
                else Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissionVideoStorage =
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_VIDEO)
            if (permissionVideoStorage != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_MEDIA_VIDEO)
            }

            val notificationPermission =
                ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            if (notificationPermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        if (listPermissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray(),
                REQUEST_ID_MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_ID_MULTIPLE_PERMISSIONS -> {
                val perms = mutableMapOf<String, Int>()

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    perms[Manifest.permission.READ_MEDIA_IMAGES] = PackageManager.PERMISSION_GRANTED
                    perms[Manifest.permission.READ_MEDIA_AUDIO] = PackageManager.PERMISSION_GRANTED
                    perms[Manifest.permission.READ_MEDIA_VIDEO] = PackageManager.PERMISSION_GRANTED
                    perms[Manifest.permission.POST_NOTIFICATIONS] =
                        PackageManager.PERMISSION_GRANTED
                } else {
                    perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] =
                        PackageManager.PERMISSION_GRANTED
                    perms[Manifest.permission.READ_EXTERNAL_STORAGE] =
                        PackageManager.PERMISSION_GRANTED
                }

                if (grantResults.isNotEmpty()) {
                    for (i in permissions.indices) {
                        perms[permissions[i]] = grantResults[i]
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (perms[Manifest.permission.READ_MEDIA_IMAGES] == PackageManager.PERMISSION_GRANTED &&
                            perms[Manifest.permission.READ_MEDIA_AUDIO] == PackageManager.PERMISSION_GRANTED &&
                            perms[Manifest.permission.READ_MEDIA_VIDEO] == PackageManager.PERMISSION_GRANTED &&
                            perms[Manifest.permission.POST_NOTIFICATIONS] == PackageManager.PERMISSION_GRANTED
                        ) {
                            Toast.makeText(
                                this,
                                "Jazakumullah, For Granting Permission.",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            handlePermissionsDenial(
                                perms, listOf(
                                    Manifest.permission.READ_MEDIA_IMAGES,
                                    Manifest.permission.READ_MEDIA_AUDIO,
                                    Manifest.permission.READ_MEDIA_VIDEO,
                                    Manifest.permission.POST_NOTIFICATIONS
                                )
                            )
                        }
                    } else {
                        if (perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED &&
                            perms[Manifest.permission.READ_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED
                        ) {
                            Toast.makeText(
                                this,
                                "Jazakumullah, For Granting Permission.",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            handlePermissionsDenial(
                                perms, listOf(
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun handlePermissionsDenial(perms: Map<String, Int>, permissions: List<String>) {
        if (permissions.any { ActivityCompat.shouldShowRequestPermissionRationale(this, it) }) {
            showDialogOK(
                "Necessary Permissions required for this app"
            ) { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> checkAndRequestPermissions()
                    DialogInterface.BUTTON_NEGATIVE -> {
                        Toast.makeText(
                            this,
                            "Necessary Permissions required for this app",
                            Toast.LENGTH_LONG
                        ).show()
                        permissionSettingScreen()
                    }
                }
            }
        } else {
            permissionSettingScreen()
        }
    }

    private fun permissionSettingScreen() {
        Toast.makeText(this, "Enable All permissions, Click On Permission", Toast.LENGTH_LONG)
            .show()
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            data = Uri.fromParts("package", packageName, null)
        }
        startActivity(intent)
        finish()
    }

    private fun showDialogOK(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", okListener)
            .create()
            .show()
    }
}