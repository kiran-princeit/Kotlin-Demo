package com.example.kotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class AlertDialogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert_dialog)

        val button = findViewById<Button>(R.id.button_dialog)

        button.setOnClickListener(View.OnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.dialogTitle)
            builder.setMessage(R.string.dialogMessage)
            builder.setIcon(R.drawable.ic_launcher_background)

            builder.setPositiveButton("Yes") { dialogInterface, which ->

                Toast.makeText(applicationContext, "clicked yes", Toast.LENGTH_LONG).show()

            }

            builder.setNeutralButton("Cancel") { dialogInterface, which ->
                Toast.makeText(
                    applicationContext,
                    "clicked cancel\n operation cancel",
                    Toast.LENGTH_LONG
                ).show()

            }
            builder.setNegativeButton("No") { dialogInterface, which ->
                Toast.makeText(applicationContext, "clicked No", Toast.LENGTH_LONG).show()
            }

            val alretdialog: AlertDialog = builder.create()
            alretdialog.setCancelable(false)
            alretdialog.show()

        })
    }
}




